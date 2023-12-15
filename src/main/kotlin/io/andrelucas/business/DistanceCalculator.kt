package io.andrelucas.business

interface DistanceCalculator {
    fun calculate(coordination: Coordination, distance: Distance): Coordination
}