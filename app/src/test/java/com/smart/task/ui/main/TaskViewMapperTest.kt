package com.smart.task.ui.main

import com.smart.task.domain.Task
import com.smart.task.domain.ForecastData
import org.junit.Assert.assertEquals
import org.junit.Test


class TaskViewMapperTest {

    private val cityViewMapper = CityViewMapper()

    @Test
    fun `map should correctly transform City to CityViewItem with forecastData`() {
        val forecastData = ForecastData(
            weather = "Clear sky",
            icon = "01d",
            currentTemperature = 25,
            low = 18,
            high = 30,
            precipitationLevel = 2,
            precipitationType = "Rain"
        )

        val city = Task(
            latitude = 12.34f,
            longitude = 56.78f,
            name = "Sabac",
            country = "Serbia",
            countryAbbr = "RS",
            forecastData = forecastData
        )

        val cityViewItem = cityViewMapper.map(city)

        assertEquals(city.hashCode(), cityViewItem.id)
        assertEquals("Sabac", cityViewItem.name)
        assertEquals("25°C", cityViewItem.temperature)
        assertEquals("01d", cityViewItem.icon)
    }

    @Test
    fun `map should handle City with no forecastData gracefully`() {
        val city = Task(
            latitude = 12.34f,
            longitude = 56.78f,
            name = "Sabac",
            country = "Serbia",
            countryAbbr = "RS"
        )

        val cityViewItem = cityViewMapper.map(city)

        assertEquals(city.hashCode(), cityViewItem.id)
        assertEquals("Sabac", cityViewItem.name)
        assertEquals("N/A", cityViewItem.temperature)
        assertEquals("", cityViewItem.icon)
    }
}
