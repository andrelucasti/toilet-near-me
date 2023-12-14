package io.andrelucas.app

import io.andrelucas.business.Coordination
import io.andrelucas.business.ToiletType

data class ToiletRequest(val name: String,
                         val coordination: Coordination,
                         val type: ToiletType)