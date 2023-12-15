package io.andrelucas.app

import io.andrelucas.business.*
import io.andrelucas.repository.ToiletRepositoryImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ToiletServiceTest : FunSpec({
    val repository: ToiletRepository = ToiletRepositoryImpl()
    val distanceCalculator: DistanceCalculator = DistanceCalculatorImpl()

    test("should return a list of toilets near a given geolocation") {

        val toilet1 = Toilet.create("T1", Coordination(1.0, 1.0), ToiletType.PUBLIC)
        val toilet2 = Toilet.create("T2", Coordination(1.1, 1.2), ToiletType.PUBLIC)
        val toilet3 = Toilet.create("T3", Coordination(1.3, 1.3), ToiletType.PUBLIC)
        val toilet4 = Toilet.create("T4", Coordination(2.0, 2.0), ToiletType.PUBLIC)

        repository.save(toilet1)
        repository.save(toilet2)
        repository.save(toilet3)
        repository.save(toilet4)

        val service = ToiletService(distanceCalculator, repository)
        val toilets = service.fetchToiletsNearby(
            Coordination(1.0, 1.0),
            Distance(100.0, DistanceUnit.KILOMETER)
        )

        toilets.size shouldBe 3
    }
})
