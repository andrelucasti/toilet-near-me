package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.business.geolocation.Geolocation

fun SchemaBuilder.coordinationSchema(){
    type<Geolocation>{
        description = "The latitude and longitude of the location"
    }

    inputType<Geolocation>{
        description = "The latitude and longitude of the location"
        name = "CoordinationInput"
    }
}