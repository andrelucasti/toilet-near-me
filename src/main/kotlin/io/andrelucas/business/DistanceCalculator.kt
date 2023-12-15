package io.andrelucas.business

interface DistanceCalculator {
    fun calculate(geolocation: Geolocation, distance: Distance): Geolocation
}