package com.smart.task.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smart.task.data.task.TaskRepositoryImpl
import com.smart.task.di.DataModule
import com.smart.task.usecases.GetAllTasksForDayUseCase

object MainModule {

    val tasksRepository = TaskRepositoryImpl(
        DataModule.dataStoreHelper,
        DataModule.remoteDataSource
    )

    fun getMainViewModel(activity: AppCompatActivity): MainViewModel {
        return ViewModelProvider(activity, mainViewModelFactory)[MainViewModel::class.java]
    }

    private val mainViewModelFactory = GenericViewModelFactory {
        MainViewModel(
            getAllTasksForDayUseCase,
            taskViewMapper
        )
    }

    private val getAllTasksForDayUseCase = GetAllTasksForDayUseCase(tasksRepository)
    private val taskViewMapper = TasksViewMapper()
}

class GenericViewModelFactory<T : ViewModel>(
    private val creator: () -> T
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        if (modelClass.isAssignableFrom(creator.invoke()::class.java)) {
            return creator.invoke() as VM
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



