package io.andrelucas.app.toilet

import io.andrelucas.business.distance.Distance
import io.andrelucas.business.distance.DistanceCalculator
import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.toilet.Toilet
import io.andrelucas.business.toilet.ToiletRepository

class ToiletService(private val distanceCalculator: DistanceCalculator,
                    private val toiletRepository: ToiletRepository
) {

    fun createToilet(toiletRequest: ToiletRequest) : String {
        val toilet = Toilet.create(toiletRequest.name, toiletRequest.geolocation, toiletRequest.type)
        toiletRepository.save(toilet)

        return toilet.id.toString()
    }

    fun fetchToiletsNearby(geolocation: Geolocation,
                           distance: Distance
    ): List<ToiletResponse> {

        val geoCoordinationCalculated = distanceCalculator.calculate(geolocation, distance)

        return toiletRepository.fetchToiletsNearby(geolocation, geoCoordinationCalculated)
                .map { ToiletResponse(it.id.toString(), it.name, it.geolocation, it.type) }
    }
}