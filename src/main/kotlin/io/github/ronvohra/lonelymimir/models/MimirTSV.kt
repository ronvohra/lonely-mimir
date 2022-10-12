package io.github.ronvohra.lonelymimir.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class MimirTSV(
    val id: Int,
    val roundNo: String,
    val questionNo: String,
    val questionText: String,
    val imageUrl: String,
    val answerText: String,
    val notes: String
)

object MimirTSVs : Table() {
    val id = integer("id").autoIncrement()
    val roundNo = varchar("roundNo", 64)
    val questionNo = varchar("questionNo", 32)
    val questionText = varchar("questionText", 16384)
    val imageUrl = varchar("imageUrl", 2048)
    val answerText = varchar("answerText", 1024)
    val notes = varchar("notes", 16384)

    override val primaryKey = PrimaryKey(id)
}