package com.smart.task.ui.main

import com.smart.task.domain.Task
import com.smart.task.domain.ForecastData
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskMergerTest {

    @Test
    fun `merge should update City with ForecastData`() {
        val forecastData = ForecastData(
            weather = "Clear sky",
            icon = "01d",
            currentTemperature = 25,
            low = 18,
            high = 30,
            precipitationLevel = 2,
            precipitationType = "Snow"
        )

        val city = Task(
            latitude = 12.34f,
            longitude = 56.78f,
            name = "Sabac",
            country = "Serbia",
            countryAbbr = "RS"
        )

        val cityMerger = CityMerger()

        val mergedCity = cityMerger.merge(forecastData, city)

        assertEquals(forecastData, mergedCity.forecastData)
        assertEquals("Sabac", mergedCity.name)
        assertEquals(12.34f, mergedCity.latitude)
        assertEquals(56.78f, mergedCity.longitude)
    }
}
