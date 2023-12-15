package io.andrelucas.business

data class Distance(val value: Double? = 10.0,
                    val unit: DistanceUnit) {
    constructor(value: DistanceUnit) : this(null, value)
}