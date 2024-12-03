package com.smart.task.ui.main

import com.smart.task.data.task.TaskRepositoryImpl
import com.smart.task.di.DataModule
import com.smart.task.usecases.GetAllTasksForDayUseCase

object MainModule {

    private val tasksRepository = TaskRepositoryImpl(
        DataModule.dataStoreHelper,
        DataModule.remoteDataSource
    )

    fun provideMainViewModel(): MainViewModel {
        val getAllTasksForDayUseCase = GetAllTasksForDayUseCase(tasksRepository)

        return MainViewModel(
            getAllTasksForDayUseCase,
            TaskViewMapper()
        )
    }
}