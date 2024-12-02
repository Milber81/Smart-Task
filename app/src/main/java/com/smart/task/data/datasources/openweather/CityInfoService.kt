package com.smart.task.data.datasources.openweather

import com.smart.task.base.SingleMapper
import com.smart.task.data.models.WeatherResponse
import com.smart.task.domain.City
import com.smart.task.domain.ForecastData
import com.smart.task.domain.repositories.CityInfoRepository
import com.smart.task.utils.API_KEY
import com.smart.task.utils.LANG
import com.smart.task.utils.UNITS
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Service implementation for fetching city information from a remote API.
 *
 * This class interacts with the [ApiClient] to fetch city details based on latitude and longitude
 * and implements the [CityInfoRepository] interface for abstraction and testability.
 *
 * @param api The API client used for network requests.
 * @param dispatcher The [CoroutineDispatcher] for managing coroutine execution (e.g., Dispatchers.IO for I/O tasks).
 */
class CityInfoService(
    private val api: ApiClient,
    private val dispatcher: CoroutineDispatcher,
    private val mapper: SingleMapper<WeatherResponse, ForecastData>
) : CityInfoRepository {

    /**
     * Fetches city information based on the provided latitude and longitude.
     *
     * @param city city object.
     * @return A [WeatherResponse] object containing details of the city.
     * @throws NullPointerException if the API response body is null.
     */
    override suspend fun getCityWeatherInfo(city: City): ForecastData {
        return withContext(dispatcher) {
            val response: Response<WeatherResponse> =
                api.getCityInfo(city.latitude, city.longitude, API_KEY, UNITS, LANG)
            mapper.map(response.body()!!)
        }
    }
}
