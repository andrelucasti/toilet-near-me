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
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

class ToiletServiceTest : FunSpec({
    val repository: ToiletRepository = ToiletRepositoryImpl()
    val distanceCalculator: DistanceCalculator = DistanceCalculatorImpl()

    beforeTest {
        repository.deleteAll()
    }

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

        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet1.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet2.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet3.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet4.name
    }

    test("should return a T2, T3 and T4 toilets near a given geolocation in a 100 meters distance") {

        val toilet1 = Toilet.create("T1", Geolocation(1.0, 1.0), ToiletType.PUBLIC)
        val toilet2 = Toilet.create("T2", Geolocation(1.1, 1.1), ToiletType.PUBLIC)
        val toilet3 = Toilet.create("T3", Geolocation(1.3, 1.4), ToiletType.PUBLIC)
        val toilet4 = Toilet.create("T4", Geolocation(1.4, 1.2), ToiletType.PUBLIC)
        val toilet5 = Toilet.create("T5", Geolocation(2.0, 2.0), ToiletType.PUBLIC)

        repository.save(toilet1)
        repository.save(toilet2)
        repository.save(toilet3)
        repository.save(toilet4)

        val service = ToiletService(distanceCalculator, repository)
        val toilets = service.fetchToiletsNearby(
            Geolocation(1.1, 1.1),
            Distance(100.0, DistanceUnit.KILOMETER)
        )

        toilets.size shouldBe 3

        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet1.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet2.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet3.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet4.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet5.name
    }

    test("should return none toilet near a given geolocation in a 100 kilometers distance") {
        val currentGeolocation = Geolocation(3.0, 4.0)

        val toilet1 = Toilet.create("T1", Geolocation(1.0, 1.0), ToiletType.PUBLIC)
        val toilet2 = Toilet.create("T2", Geolocation(1.1, 1.1), ToiletType.PUBLIC)
        val toilet3 = Toilet.create("T3", Geolocation(1.3, 1.4), ToiletType.PUBLIC)
        val toilet4 = Toilet.create("T4", Geolocation(1.4, 1.2), ToiletType.PUBLIC)
        val toilet5 = Toilet.create("T5", Geolocation(2.0, 2.0), ToiletType.PUBLIC)

        repository.save(toilet1)
        repository.save(toilet2)
        repository.save(toilet3)
        repository.save(toilet4)

        val service = ToiletService(distanceCalculator, repository)
        val toilets = service.fetchToiletsNearby(
            currentGeolocation,
            Distance(100.0, DistanceUnit.KILOMETER)
        )

        toilets.size shouldBe 0

        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet1.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet2.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet3.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet4.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet5.name
    }

    test("should return all toilets near a given geolocation in a 2 km distance"){
        val currentGeolocation = Geolocation(38.72691878745993, -9.111318592340758)

        val toilet0 = Toilet.create("T0", Geolocation(38.72691878745993, -9.111318592340758), ToiletType.PRIVATE) // 0.0 km
        val toilet1 = Toilet.create("T1", Geolocation(38.74045, -9.09405), ToiletType.PUBLIC) // 1.5 km
        val toilet2 = Toilet.create("T2", Geolocation(38.74226, -9.09405), ToiletType.PUBLIC) // 1.7N 1.5E km
        val toilet3 = Toilet.create("T3", Geolocation(38.74496, -9.08829), ToiletType.PUBLIC) // 2.0 km

        repository.save(toilet0)
        repository.save(toilet1)
        repository.save(toilet2)
        repository.save(toilet3)

        val service = ToiletService(distanceCalculator, repository)
        val toilets = service.fetchToiletsNearby(
            currentGeolocation,
            Distance(2.0, DistanceUnit.KILOMETER)
        )

        toilets.size shouldBe 4

        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet0.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet1.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet2.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet3.name
    }

    test("should return toilets near a given geolocation in a 2 km distance"){
        val currentGeolocation = Geolocation(38.72691878745993, -9.111318592340758)

        val toilet0 = Toilet.create("T0", Geolocation(38.72691878745993, -9.111318592340758), ToiletType.PRIVATE) // 0.0 km
        val toilet1 = Toilet.create("T1", Geolocation(38.74045, -9.09405), ToiletType.PUBLIC) // 1.5 km
        val toilet2 = Toilet.create("T2", Geolocation(38.74226, -9.09405), ToiletType.PUBLIC) // 1.7N 1.5E km
        val toilet3 = Toilet.create("T3", Geolocation(38.74496, -9.08829), ToiletType.PUBLIC) // 2.0 km
        val toilet4 = Toilet.create("T4", Geolocation(38.74586, -9.09405), ToiletType.PUBLIC) // 2.1N 1.5E km
        val toilet5 = Toilet.create("T5", Geolocation(38.74586, -9.08829), ToiletType.PUBLIC) // 2.1N 2.0E km
        val toilet6 = Toilet.create("T6", Geolocation(38.74586, -9.08253), ToiletType.PUBLIC) // 2.1N 2.5E km

        repository.save(toilet0)
        repository.save(toilet1)
        repository.save(toilet2)
        repository.save(toilet3)
        repository.save(toilet4)
        repository.save(toilet5)
        repository.save(toilet6)

        val service = ToiletService(distanceCalculator, repository)
        val toilets = service.fetchToiletsNearby(
            currentGeolocation,
            Distance(2.0, DistanceUnit.KILOMETER)
        )

        toilets.size shouldBe 4

        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet0.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet1.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet2.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldContain toilet3.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet4.name
        toilets.map { toiletResponse -> toiletResponse.name } shouldNotContain toilet5.name
    }
})
