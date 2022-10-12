package io.github.ronvohra.lonelymimir

import io.github.ronvohra.lonelymimir.db.DatabaseFactory
import io.github.ronvohra.lonelymimir.plugins.configureDependencyInjection
import io.github.ronvohra.lonelymimir.plugins.configureMonitoring
import io.github.ronvohra.lonelymimir.plugins.configureRouting
import io.github.ronvohra.lonelymimir.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureDependencyInjection()
    configureRouting()
    configureSerialization()
    configureMonitoring()
}
