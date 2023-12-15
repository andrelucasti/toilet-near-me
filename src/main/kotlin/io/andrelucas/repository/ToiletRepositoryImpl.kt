package io.andrelucas.repository

import io.andrelucas.business.Coordination
import io.andrelucas.business.Toilet
import io.andrelucas.business.ToiletRepository

class ToiletRepositoryImpl : ToiletRepository {

    private var data = listOf<Toilet>()

    override fun save(toilet: Toilet) {
        data = data + toilet
    }

    override fun fetchToiletsNearby(startAt: Coordination, endAt: Coordination): List<Toilet> {
        return data.filter { it.coordination.latitude >= startAt.latitude && it.coordination.latitude <= endAt.latitude }
                .filter { it.coordination.longitude >= startAt.longitude && it.coordination.longitude <= endAt.longitude }
    }
}
