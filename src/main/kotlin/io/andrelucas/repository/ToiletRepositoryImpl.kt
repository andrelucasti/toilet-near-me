package io.andrelucas.repository

import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.toilet.Toilet
import io.andrelucas.business.toilet.ToiletRepository

class ToiletRepositoryImpl : ToiletRepository {

    private val data = mutableListOf<Toilet>()

    override fun save(toilet: Toilet) {
        data.add(toilet)
    }

    override fun fetchToiletsNearby(startAt: Geolocation, endAt: Geolocation): List<Toilet> {
        return data.filter { it.geolocation.latitude >= startAt.latitude && it.geolocation.latitude <= endAt.latitude }
                .filter { it.geolocation.longitude >= startAt.longitude && it.geolocation.longitude <= endAt.longitude }
            .map { Toilet(it.id, it.name, it.geolocation, it.type) }
    }

    override fun deleteAll() {
        data.clear()
    }
}
