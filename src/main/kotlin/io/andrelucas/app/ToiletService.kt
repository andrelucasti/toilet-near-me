package io.andrelucas.app

import io.andrelucas.business.*

class ToiletService(private val distanceCalculator: DistanceCalculator,
                    private val toiletRepository: ToiletRepository) {

    fun createToilet(toiletRequest: ToiletRequest) : String {
        val toilet = Toilet.create(toiletRequest.name, toiletRequest.geolocation, toiletRequest.type)
        toiletRepository.save(toilet)

        return toilet.id.toString()
    }

    fun fetchToiletsNearby(geolocation: Geolocation,
                           distance: Distance): List<ToiletResponse> {
        val geoCoordinationCalculated = distanceCalculator.calculate(geolocation, distance)

        return toiletRepository.fetchToiletsNearby(geolocation, geoCoordinationCalculated)
                .map { ToiletResponse(it.id.toString(), it.name, it.geolocation, it.type) }
    }
}