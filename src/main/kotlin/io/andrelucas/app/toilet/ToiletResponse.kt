package io.andrelucas.app.toilet

import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.toilet.ToiletType

data class ToiletResponse(val id: String,
                          val name: String,
                          val geolocation: Geolocation,
                          val type: ToiletType
)