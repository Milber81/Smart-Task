package com.smart.task.ui.main

import com.smart.task.data.task.TaskRepositoryImpl
import com.smart.task.di.DataModule
import com.smart.task.usecases.GetAllTasksUseCase

object MainModule {

    private val tasksRepository = TaskRepositoryImpl(
        DataModule.dataStoreHelper,
        DataModule.remoteDataSource
    )

    fun provideMainViewModel(): MainViewModel {
        val getAllTasksUseCase = GetAllTasksUseCase(tasksRepository)

        return MainViewModel(
            getAllTasksUseCase,
        )
    }
}