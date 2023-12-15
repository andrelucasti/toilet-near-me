package io.andrelucas.plugins

import com.apurebase.kgraphql.GraphQL
import io.andrelucas.infrastructure.coordinationSchema
import io.andrelucas.infrastructure.distanceSchema
import io.andrelucas.infrastructure.toiletSchema
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers

fun Application.configureGraphQL() {
    install(GraphQL){
        playground = true
        useDefaultPrettyPrinter = true
        coroutineDispatcher = Dispatchers.Default
        schema {
            toiletSchema()
            coordinationSchema()
            distanceSchema()
        }
    }
}
