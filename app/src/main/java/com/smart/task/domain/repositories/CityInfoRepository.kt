package com.smart.task.domain.repositories

import com.smart.task.domain.City
import com.smart.task.domain.ForecastData

fun interface CityInfoRepository{
    suspend fun getCityWeatherInfo(city: City): ForecastData
}

