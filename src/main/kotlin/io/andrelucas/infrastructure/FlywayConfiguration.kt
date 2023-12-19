package io.andrelucas.infrastructure

import io.ktor.server.application.*
import org.flywaydb.core.Flyway

object FlywayConfiguration {
    fun init(environment: ApplicationEnvironment) {

        val url = environment.config.property("ktor.datasource.url").getString()
        val user = environment.config.property("ktor.datasource.username").getString()
        val password = environment.config.property("ktor.datasource.password").getString()

        val flyway = Flyway.configure()
            .locations("classpath:db/migration")
            .dataSource(url, user, password)
            .load()

        flyway.migrate()
    }
}