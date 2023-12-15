package io.andrelucas.app

import io.andrelucas.business.geolocation.Geolocation
import io.andrelucas.business.distance.Distance
import io.andrelucas.business.distance.DistanceCalculator
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.pow


//https://forest.moscowfsl.wsu.edu/fswepp/rc/kmlatcon.html
class DistanceCalculatorImpl : DistanceCalculator {
    override fun calculate(geolocation: Geolocation,
                           distance: Distance
    ): Geolocation {

        val kilometer = distance.value
        val newLatitude = newLatitude(kilometer, geolocation)
        val newLongitude = newLongitude(kilometer, geolocation)

        return Geolocation(newLatitude, newLongitude)
    }

    private fun newLatitude(kilometerEast: Double, geolocation: Geolocation): Double{
        val oneDegreeLatitude = degreeAtEquatorInKm( 68.875)
        val deltaLatitude = delta(kilometerEast, oneDegreeLatitude)
        return round(geolocation.latitude + deltaLatitude)
    }

    private fun newLongitude(kilometerEast: Double,
                             geolocation: Geolocation
    ): Double {

        val longitudeDegree = cos(degreeToRadians(geolocation.latitude)) * degreeAtEquatorInKm(69.172)
        val deltaLongitude = delta(kilometerEast, longitudeDegree)

        return round(geolocation.longitude + deltaLongitude)
    }

    private fun degreeAtEquatorInKm(degree: Double): Double {
        return degree * 1.6093
    }

    // To convert from degrees to radians, multiply the number of degrees by π/180
    private fun degreeToRadians(degree: Double): Double {
        return degree * Math.PI / 180
    }

    private fun delta(kilometer: Double, degree: Double): Double {
        return kilometer / degree
    }

    private fun round(realNumber:Double): Double {
        val mathRound = Math.round(5.0)
        val factor = 10.0.pow(mathRound.toDouble())
        val intermed = realNumber * factor + 0.5
        return floor(intermed) / factor
    }
}

