package io.andrelucas.app

import io.andrelucas.business.Coordination
import io.andrelucas.business.ToiletType

data class ToiletResponse(val id: String,
                          val name: String,
                          val coordination: Coordination,
                          val type: ToiletType)