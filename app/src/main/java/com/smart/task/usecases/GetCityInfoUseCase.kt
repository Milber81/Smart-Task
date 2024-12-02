package com.smart.task.usecases

import com.smart.task.domain.City
import com.smart.task.domain.ForecastData
import com.smart.task.domain.repositories.CityInfoRepository

class GetCityInfoUseCase(
    private val repository: CityInfoRepository
) {
    suspend fun getCityInfo(city: City): ForecastData =
        repository.getCityWeatherInfo(city)
}
