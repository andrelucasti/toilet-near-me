package io.andrelucas

import io.andrelucas.infrastructure.FlywayConfiguration
import io.andrelucas.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    FlywayConfiguration.init(environment)

    configureMonitoring()
    configureSerialization()
    configureGraphQL()
}
