package io.github.ronvohra.lonelymimir.routes

import io.github.ronvohra.lonelymimir.dao.TSVRepository
import io.github.ronvohra.lonelymimir.parsers.readCsv
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.File

private const val FILE_PATH = "/uploads/quiz.tsv"
private val logger = LoggerFactory.getLogger(Application::class.simpleName)

fun Route.tsvRouting() {
    val repository by inject<TSVRepository>()

    route("/upload") {
        post {
            // retrieve all multipart data (suspending)
            val multipart = call.receiveMultipart()
            var fileOutput = byteArrayOf()
            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        var fileBytes = part.streamProvider().readBytes()
                        fileOutput += fileBytes
                    }

                    else -> {}
                }
                part.dispose()
            }
//            multipart.forEachPart { part ->
//                // if part is a file (could be form item)
//                if (part is PartData.FileItem) {
//                    // retrieve file name of upload
//
//
//                    // use InputStream from part to save file
//                    part.streamProvider().use { its ->
//                        // copy the stream to the file with buffering
//                        file.outputStream().buffered().use {
//                            // note that this is blocking
//                            its.copyTo(it)
//                        }
//                    }
//                }
//                // make sure to dispose of the part after use to prevent leaks
//                part.dispose()
//            }
            launch {
                val questions = readCsv(ByteArrayInputStream(fileOutput))
                questions.map {
                    repository.add(
                        it.roundNo,
                        it.questionNo,
                        it.questionText,
                        it.imageUrl,
                        it.answerText,
                        it.notes
                    )
                }
                logger.info("Ingested questions successfully")
            }
            call.respondText("Question stored correctly", status = HttpStatusCode.Created)
        }
    }

    route("/question") {
        get {
            val mimirTSVs = repository.getAll()
            if (mimirTSVs.isNotEmpty()) call.respond(mimirTSVs) else call.respondText(
                "No questions found",
                status = HttpStatusCode.OK
            )
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val mimirTSV =
                repository.get(id.toInt()) ?: return@get call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(mimirTSV)
        }
        post {
            val roundNo = call.parameters.getOrFail("roundNo")
            val questionNo = call.parameters.getOrFail("questionNo")
            val questionText = call.parameters.getOrFail("questionText")
            val imageUrl = call.parameters.getOrFail("imageUrl")
            val answerText = call.parameters.getOrFail("answerText")
            val notes = call.parameters.getOrFail("notes")

            repository.add(roundNo, questionNo, questionText, imageUrl, answerText, notes)
            call.respondText("Question stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            repository.delete(id.toInt())
            call.respondText("Question removed correctly", status = HttpStatusCode.Accepted)
        }
    }
}
