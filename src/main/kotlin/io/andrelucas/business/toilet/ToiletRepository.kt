package io.andrelucas.business.toilet

import io.andrelucas.business.geolocation.Geolocation

interface ToiletRepository {

    fun save(toilet: Toilet)
    fun fetchToiletsNearby(startAt: Geolocation,
                           endAt: Geolocation
    ): List<Toilet>
    fun deleteAll()
}