package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.app.ToiletRequest
import io.andrelucas.app.ToiletResponse
import io.andrelucas.app.ToiletService
import io.andrelucas.business.Coordination
import io.andrelucas.business.Distance
import io.andrelucas.business.DistanceUnit
import io.andrelucas.business.ToiletType
import java.util.UUID

fun SchemaBuilder.toiletSchema(toiletService: ToiletService) {
    enum<ToiletType> {
        description = "The type of toilet"
    }

    type<ToiletResponse>{
        description = "The toilet response"
    }

    inputType<ToiletRequest>{
        description = "The toilet request"
        name = "ToiletRequestInput"
    }

    mutation("createToilet"){
        description = "Create a toilet"
        resolver { toiletRequest: ToiletRequest -> toiletService.createToilet(toiletRequest) }
    }

    query("toiletsNearby"){
        resolver { currentCoordination: Coordination, distanceInKm: Double ->
            toiletService.fetchToiletsNearby(currentCoordination, Distance(distanceInKm, DistanceUnit.KILOMETER))
        }
    }
}

