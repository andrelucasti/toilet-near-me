package io.andrelucas.app

import io.andrelucas.business.*

class ToiletService(private val distanceCalculator: DistanceCalculator,
                    private val toiletRepository: ToiletRepository) {

    fun createToilet(toiletRequest: ToiletRequest) : String {
        val toilet = Toilet.create(toiletRequest.name, toiletRequest.coordination, toiletRequest.type)
        toiletRepository.save(toilet)

        return toilet.id.toString()
    }

    fun fetchToiletsNearby(coordination: Coordination,
                           distance: Distance): List<ToiletResponse> {
        val geoCoordinationCalculated = distanceCalculator.calculate(coordination, distance)

        return toiletRepository.fetchToiletsNearby(coordination, geoCoordinationCalculated)
                .map { ToiletResponse(it.id.toString(), it.name, it.coordination, it.type) }
    }
}