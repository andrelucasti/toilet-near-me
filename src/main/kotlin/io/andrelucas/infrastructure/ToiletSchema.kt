package io.andrelucas.infrastructure

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.andrelucas.app.ToiletRequest
import io.andrelucas.app.ToiletResponse
import io.andrelucas.business.Coordination
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

    query("toiletsNearby"){
        resolver { currentCoordination: Coordination ->
            listOf(
                ToiletResponse(
                    id = UUID.randomUUID().toString(),
                    name = "Toilet 1",
                    coordination = Coordination(1.0, 1.0),
                    type = ToiletType.PUBLIC
                ),
                ToiletResponse(
                    id = UUID.randomUUID().toString(),
                    name = "Toilet 2",
                    coordination = Coordination(2.0, 2.0),
                    type = ToiletType.PUBLIC
                ),
                ToiletResponse(
                    id = UUID.randomUUID().toString(),
                    name = "Toilet 3",
                    coordination = Coordination(3.0, 3.0),
                    type = ToiletType.PUBLIC
                )
            )
        }
    }
}

