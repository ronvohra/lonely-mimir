package io.github.ronvohra.lonelymimir.plugins

import io.github.ronvohra.lonelymimir.di.repositoryModule
import org.koin.core.context.GlobalContext

fun configureDependencyInjection() {
    GlobalContext.startKoin {
        modules(
//            databaseModule,
            repositoryModule,
//            clientModule,
//            controllersModule,
//            authenticationModule,
        )
    }
}
