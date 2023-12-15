package io.andrelucas.repository

import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.toilet.Toilet
import io.andrelucas.business.toilet.ToiletRepository

class ToiletRepositoryImpl : ToiletRepository {

    private var data = listOf<Toilet>()

    override fun save(toilet: Toilet) {
        data = data + toilet
    }

    override fun fetchToiletsNearby(startAt: Geolocation, endAt: Geolocation): List<Toilet> {
        return data.filter { it.geolocation.latitude >= startAt.latitude && it.geolocation.latitude <= endAt.latitude }
                .filter { it.geolocation.longitude >= startAt.longitude && it.geolocation.longitude <= endAt.longitude }
    }
}
