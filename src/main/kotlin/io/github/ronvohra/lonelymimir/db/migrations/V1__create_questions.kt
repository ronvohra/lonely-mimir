package io.github.ronvohra.lonelymimir.db.migrations

import io.github.ronvohra.lonelymimir.models.MimirTSVs
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class V1__create_questions: BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(MimirTSVs)
        }
    }
}
