package io.andrelucas.app

import io.andrelucas.business.Coordination
import io.andrelucas.business.Distance
import io.andrelucas.business.DistanceCalculator
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.pow

class DistanceCalculatorImpl : DistanceCalculator {
    override fun calculate(coordination: Coordination,
                           distance: Distance): Coordination {

        val kilometer = distance.value!!
        val newLatitude = newLatitude(kilometer, coordination)
        val newLongitude = newLongitude(kilometer, coordination)

        return Coordination(newLatitude, newLongitude)
    }

    private fun newLatitude(kilometerEast: Double, coordination: Coordination): Double{
        val oneDegreeLatitude = degreeAtEquatorInKm( 68.875)
        val deltaLatitude = delta(kilometerEast, oneDegreeLatitude)
        return round(coordination.latitude + deltaLatitude)
    }

    private fun newLongitude(kilometerEast: Double,
                             coordination: Coordination): Double {

        val longitudeDegree = cos(degreeToRadians(coordination.latitude)) * degreeAtEquatorInKm(69.172)
        val deltaLongitude = delta(kilometerEast, longitudeDegree)

        return round(coordination.longitude + deltaLongitude)
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

