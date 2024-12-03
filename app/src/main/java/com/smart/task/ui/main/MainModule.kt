package com.smart.task.ui.main

import com.smart.task.data.task.TaskRepositoryImpl
import com.smart.task.di.DataModule
import com.smart.task.usecases.GetAllTasksForTodayUseCase

object MainModule {

    private val tasksRepository = TaskRepositoryImpl(
        DataModule.dataStoreHelper,
        DataModule.remoteDataSource
    )

    fun provideMainViewModel(): MainViewModel {
        val getAllTasksForTodayUseCase = GetAllTasksForTodayUseCase(tasksRepository)

        return MainViewModel(
            getAllTasksForTodayUseCase,
        )
    }
}