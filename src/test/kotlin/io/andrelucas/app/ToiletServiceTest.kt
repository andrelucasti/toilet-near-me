package io.andrelucas.app

import io.andrelucas.app.toilet.ToiletService
import io.andrelucas.business.distance.Distance
import io.andrelucas.business.distance.DistanceCalculator
import io.andrelucas.business.distance.DistanceUnit
import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.toilet.Toilet
import io.andrelucas.business.toilet.ToiletRepository
import io.andrelucas.business.toilet.ToiletType
import io.andrelucas.repository.ToiletRepositoryImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ToiletServiceTest : FunSpec({
    val repository: ToiletRepository = ToiletRepositoryImpl()
    val distanceCalculator: DistanceCalculator = DistanceCalculatorImpl()

    test("should return a list of toilets near a given geolocation") {

        val toilet1 = Toilet.create("T1", Geolocation(1.0, 1.0), ToiletType.PUBLIC)
        val toilet2 = Toilet.create("T2", Geolocation(1.1, 1.2), ToiletType.PUBLIC)
        val toilet3 = Toilet.create("T3", Geolocation(1.3, 1.3), ToiletType.PUBLIC)
        val toilet4 = Toilet.create("T4", Geolocation(2.0, 2.0), ToiletType.PUBLIC)

        repository.save(toilet1)
        repository.save(toilet2)
        repository.save(toilet3)
        repository.save(toilet4)

        val service = ToiletService(distanceCalculator, repository)
        val toilets = service.fetchToiletsNearby(
            Geolocation(1.0, 1.0),
            Distance(100.0, DistanceUnit.KILOMETER)
        )

        toilets.size shouldBe 3
    }
})
