package io.andrelucas.business

import java.util.UUID

data class Toilet(val uuid: UUID, val name:String, val coordination: Coordination, val type: ToiletType){
    companion object {
        fun create(name: String, coordination: Coordination, type: ToiletType): Toilet {
            return Toilet(UUID.randomUUID(), name, coordination, type)
        }
    }
}