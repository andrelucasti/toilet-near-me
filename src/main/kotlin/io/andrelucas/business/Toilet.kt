package io.andrelucas.business

import java.util.UUID

data class Toilet(val uuid: UUID, val name:String, val coordination: Coordination, val type: ToiletType)