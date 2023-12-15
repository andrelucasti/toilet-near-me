package io.andrelucas.business

interface ToiletRepository {

    fun save(toilet: Toilet)
    fun fetchToiletsNearby(startAt: Coordination,
                           endAt: Coordination): List<Toilet>
}