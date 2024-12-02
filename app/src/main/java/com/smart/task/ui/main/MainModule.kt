package com.smart.task.ui.main

import com.smart.task.data.datasources.openweather.CityInfoService
import com.smart.task.data.mappers.ForecastDataDtoMapper
import com.smart.task.di.CoroutinesModule
import com.smart.task.di.DataStoreModule
import com.smart.task.di.NetworkModule
import com.smart.task.usecases.GetCityInfoUseCase

object MainModule {

    private val apiCityInfoService = CityInfoService(
        NetworkModule.provideApiClient(),
        CoroutinesModule.provideCoroutineDispatcher(),
        ForecastDataDtoMapper()
    )

    fun provideMainViewModel(): MainViewModel {
        val getCityInfoUseCase = GetCityInfoUseCase(apiCityInfoService)
        val dataStoreHelper = DataStoreModule.dataStoreHelper
        val mapper = CityListViewMapper()
        val merger = CityMerger()

        return MainViewModel(
            citiesRepository = dataStoreHelper,
            getCityInfoUseCase = getCityInfoUseCase,
            dispatcher = CoroutinesModule.provideCoroutineDispatcher(),
            mapper,
            merger
        )
    }
}