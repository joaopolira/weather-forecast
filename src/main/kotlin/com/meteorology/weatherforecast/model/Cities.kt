package com.meteorology.weatherforecast.model

class Cities(
    private val cities: List<String>
) {

    companion object {
        fun create(
            cities: List<String>?
        ): Cities {
            return Cities(cities ?: emptyList())
        }
    }

    val isNotEmpty get() = cities.isNotEmpty() ?: false

    val count get() = cities.count()

    fun forEach (callback: (city: String) -> Unit) {
        cities.forEach {
            callback(it)
        }
    }
}