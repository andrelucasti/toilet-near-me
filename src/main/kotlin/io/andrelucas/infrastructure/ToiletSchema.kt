package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.app.toilet.ToiletRequest
import io.andrelucas.app.toilet.ToiletResponse
import io.andrelucas.app.toilet.ToiletService
import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.distance.Distance
import io.andrelucas.business.distance.DistanceUnit
import io.andrelucas.business.toilet.ToiletType

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
        resolver { currentGeolocation: Geolocation, distanceInKm: Double ->
            toiletService.fetchToiletsNearby(currentGeolocation, Distance(distanceInKm, DistanceUnit.KILOMETER))
        }
    }
}

