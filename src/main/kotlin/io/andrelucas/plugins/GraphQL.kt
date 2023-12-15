package io.andrelucas.plugins

import com.apurebase.kgraphql.GraphQL
import io.andrelucas.app.DistanceCalculatorImpl
import io.andrelucas.app.toilet.ToiletService
import io.andrelucas.infrastructure.coordinationSchema
import io.andrelucas.infrastructure.distanceSchema
import io.andrelucas.infrastructure.toiletSchema
import io.andrelucas.repository.ToiletRepositoryImpl
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers


private val toiletService = ToiletService(DistanceCalculatorImpl(), ToiletRepositoryImpl())
fun Application.configureGraphQL() {
    install(GraphQL){
        playground = true
        useDefaultPrettyPrinter = true
        coroutineDispatcher = Dispatchers.Default
        schema {
            toiletSchema(toiletService)
            coordinationSchema()
            distanceSchema()
        }
    }
}
