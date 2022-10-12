package io.github.ronvohra.lonelymimir.dao

import io.github.ronvohra.lonelymimir.db.DatabaseFactory.dbQuery
import io.github.ronvohra.lonelymimir.models.MimirTSV
import io.github.ronvohra.lonelymimir.models.MimirTSVs
import org.jetbrains.exposed.sql.*

class SQLDatabaseTSVRepository : TSVRepository {
    override suspend fun getAll(): List<MimirTSV> = dbQuery {
        MimirTSVs.selectAll().map(::resultRowToUser)
    }
    override suspend fun get(id: Int): MimirTSV? = dbQuery {
        MimirTSVs
            .select { MimirTSVs.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun add(roundNo: String, questionNo: String, questionText: String, imageUrl: String, answerText: String, notes: String): MimirTSV? = dbQuery {
        val insertStatement = MimirTSVs.insert {
            it[MimirTSVs.roundNo] = roundNo
            it[MimirTSVs.questionNo] = questionNo
            it[MimirTSVs.questionText] = questionText
            it[MimirTSVs.imageUrl] = imageUrl
            it[MimirTSVs.answerText] = answerText
            it[MimirTSVs.notes] = notes
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun update(id: Int, roundNo: String, questionNo: String, questionText: String, imageUrl: String, answerText: String, notes: String): Boolean = dbQuery {
        MimirTSVs.update({ MimirTSVs.id eq id }) {
            it[MimirTSVs.roundNo] = roundNo
            it[MimirTSVs.questionNo] = questionNo
            it[MimirTSVs.questionText] = questionText
            it[MimirTSVs.imageUrl] = imageUrl
            it[MimirTSVs.answerText] = answerText
            it[MimirTSVs.notes] = notes
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        MimirTSVs.deleteWhere { MimirTSVs.id eq id } > 0
    }

    private fun resultRowToUser(row: ResultRow) = MimirTSV(
        id = row[MimirTSVs.id],
        roundNo = row[MimirTSVs.roundNo],
        questionNo = row[MimirTSVs.questionNo],
        questionText = row[MimirTSVs.questionText],
        imageUrl = row[MimirTSVs.imageUrl],
        answerText = row[MimirTSVs.answerText],
        notes = row[MimirTSVs.notes],
    )
}