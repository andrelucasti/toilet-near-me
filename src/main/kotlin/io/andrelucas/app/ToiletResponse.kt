package io.andrelucas.app

import io.andrelucas.business.Geolocation
import io.andrelucas.business.ToiletType

data class ToiletResponse(val id: String,
                          val name: String,
                          val geolocation: Geolocation,
                          val type: ToiletType)