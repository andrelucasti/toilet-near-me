package io.andrelucas.business.distance

import io.andrelucas.business.geolocation.Geolocation

interface DistanceCalculator {
    fun calculate(geolocation: Geolocation, distance: Distance): Geolocation
}