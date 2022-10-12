package io.github.ronvohra.lonelymimir.parsers

import io.github.ronvohra.lonelymimir.models.MimirTSV
import org.apache.commons.csv.CSVFormat
import java.io.InputStream

fun readCsv(inputStream: InputStream): List<MimirTSV> =
    CSVFormat.Builder.create(CSVFormat.MONGODB_TSV).apply {
        setIgnoreSurroundingSpaces(true)
    }.build().parse(inputStream.reader())
        .drop(1) // Dropping the header
        .mapIndexed { idx, it ->
            MimirTSV(
                id = idx + 1,
                roundNo = it[0],
                questionNo = it[1],
                questionText = it[2],
                imageUrl = it[3],
                answerText = it[4],
                notes = it[5]
            )
        }
