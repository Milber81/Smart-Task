package com.smart.task.domain.repositories

import com.smart.task.domain.City

interface CitiesRepository {

    suspend fun getAllCities(): List<City>

    suspend fun addCity(city: City)

    suspend fun removeCity(city: City)
}