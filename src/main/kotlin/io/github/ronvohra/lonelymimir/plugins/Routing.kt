package io.github.ronvohra.lonelymimir.plugins

import io.github.ronvohra.lonelymimir.routes.tsvRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        tsvRouting()
    }
}
