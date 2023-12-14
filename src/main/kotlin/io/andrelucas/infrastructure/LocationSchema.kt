package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.business.Coordination

fun SchemaBuilder.coordinationSchema(){
    type<Coordination>{
        description = "The latitude and longitude of the location"
    }

    inputType<Coordination>{
        description = "The latitude and longitude of the location"
        name = "CoordinationInput"
    }
}