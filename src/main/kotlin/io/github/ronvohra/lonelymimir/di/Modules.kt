package io.github.ronvohra.lonelymimir.di

import io.github.ronvohra.lonelymimir.dao.SQLDatabaseTSVRepository
import io.github.ronvohra.lonelymimir.dao.TSVRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TSVRepository> { SQLDatabaseTSVRepository() }
}
