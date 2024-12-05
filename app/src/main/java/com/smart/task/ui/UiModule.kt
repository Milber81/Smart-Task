package com.smart.task.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smart.task.ui.main.GenericViewModelFactory
import com.smart.task.ui.main.MainModule
import com.smart.task.ui.main.TaskViewMapper
import com.smart.task.usecases.AddTaskCommentUseCase
import com.smart.task.usecases.GetTaskByIdUseCase
import com.smart.task.usecases.SetTaskStatusUseCase

object UiModule {

    private val getTaskByIdUseCase = GetTaskByIdUseCase(MainModule.tasksRepository)
    private val setTaskStatusUseCase = SetTaskStatusUseCase(MainModule.tasksRepository)
    private val addTaskCommentUseCase = AddTaskCommentUseCase(MainModule.tasksRepository)
    private val singleTaskMapper = TaskViewMapper()

    private val sharedViewModelFactory = GenericViewModelFactory {
        SharedViewModel(
            getTaskByIdUseCase,
            setTaskStatusUseCase,
            addTaskCommentUseCase,
            singleTaskMapper,
        )
    }

    fun getSharedViewModel(activity: AppCompatActivity): SharedViewModel {
        return ViewModelProvider(
            activity, sharedViewModelFactory)[SharedViewModel::class.java]
    }
}
