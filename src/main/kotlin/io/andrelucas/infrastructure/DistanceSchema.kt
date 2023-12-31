package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.business.distance.Distance
import io.andrelucas.business.distance.DistanceUnit

fun SchemaBuilder.distanceSchema(){
    enum<DistanceUnit>{
        description = "The unit of distance"
    }

    type<Distance>{
        description = "The distance between two locations"
    }

    inputType<Distance>{
        description = "The distance between two locations"
        name = "DistanceInput"
    }
}