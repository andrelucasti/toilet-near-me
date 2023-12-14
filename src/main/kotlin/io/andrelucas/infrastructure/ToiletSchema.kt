package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.app.ToiletRequest
import io.andrelucas.app.ToiletResponse
import io.andrelucas.business.ToiletType
import java.util.UUID

fun SchemaBuilder.toiletSchema() {
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
        resolver { toiletRequest: ToiletRequest ->

            println(toiletRequest)

           UUID.randomUUID().toString()
        }
    }

    query("toilet"){
        resolver { ->
            "toilet"
        }
    }
}

