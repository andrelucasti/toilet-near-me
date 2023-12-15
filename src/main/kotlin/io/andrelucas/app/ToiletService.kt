package io.andrelucas.app

import io.andrelucas.business.*

class ToiletService(private val distanceCalculator: DistanceCalculator,
                    private val toiletRepository: ToiletRepository) {

    fun fetchToiletsNearby(coordination: Coordination,
                           distance: Distance): List<Toilet> {

        val geoCoordinationCalculated = distanceCalculator.calculate(coordination, distance)
        return toiletRepository.fetchToiletsNearby(coordination, geoCoordinationCalculated)
    }
}