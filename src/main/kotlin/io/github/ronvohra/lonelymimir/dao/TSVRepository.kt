package io.github.ronvohra.lonelymimir.dao

import io.github.ronvohra.lonelymimir.models.MimirTSV

interface TSVRepository {
    suspend fun getAll(): List<MimirTSV>
    suspend fun get(id: Int): MimirTSV?
    suspend fun add(roundNo: String, questionNo: String, questionText: String, imageUrl: String, answerText: String, notes: String): MimirTSV?
    suspend fun update(id: Int, roundNo: String, questionNo: String, questionText: String, imageUrl: String, answerText: String, notes: String): Boolean
    suspend fun delete(id: Int): Boolean
}