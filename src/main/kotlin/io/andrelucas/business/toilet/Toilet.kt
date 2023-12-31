package io.andrelucas.business.toilet

import io.andrelucas.business.geolocation.Geolocation
import java.util.UUID

data class Toilet(val id: UUID,
                  val name:String,
                  val geolocation: Geolocation,
                  val type: ToiletType
){
    companion object {
        fun create(name: String, geolocation: Geolocation, type: ToiletType): Toilet {
            return Toilet(UUID.randomUUID(), name, geolocation, type)
        }
    }
}