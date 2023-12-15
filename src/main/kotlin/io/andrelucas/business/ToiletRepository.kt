package io.andrelucas.business

interface ToiletRepository {

    fun save(toilet: Toilet)
    fun fetchToiletsNearby(startAt: Geolocation,
                           endAt: Geolocation): List<Toilet>
}