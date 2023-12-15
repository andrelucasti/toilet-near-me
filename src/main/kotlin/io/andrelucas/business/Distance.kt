package io.andrelucas.business

data class Distance(val value: Double = 10.0,
                    val unit: DistanceUnit) {
    constructor(unit: DistanceUnit) : this(10.0, unit)
}