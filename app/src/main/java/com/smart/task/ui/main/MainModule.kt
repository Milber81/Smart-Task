package com.smart.task.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smart.task.base.ListMapper
import com.smart.task.data.task.TaskRepositoryImpl
import com.smart.task.di.DataModule
import com.smart.task.domain.Task
import com.smart.task.usecases.GetAllTasksForDayUseCase

object MainModule {

    val tasksRepository = TaskRepositoryImpl(
        DataModule.dataStoreHelper,
        DataModule.remoteDataSource
    )

    class MainViewModelFactory(
        private val getAllTasksForDayUseCase: GetAllTasksForDayUseCase,
        private val mapper: ListMapper<Task, TaskViewItem>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(getAllTasksForDayUseCase, mapper) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    val getAllTasksForDayUseCase = GetAllTasksForDayUseCase(tasksRepository)
    val taskViewMapper = TasksViewMapper()
}