package com.smart.task.ui

import com.smart.task.ui.main.MainModule
import com.smart.task.ui.main.TaskViewMapper
import com.smart.task.usecases.GetTaskByIdUseCase
import com.smart.task.usecases.SetTaskResolvedUseCase

object UiModule {

    private val getTaskByIdUseCase = GetTaskByIdUseCase(MainModule.tasksRepository)
    private val setTaskResolvedUseCase = SetTaskResolvedUseCase(MainModule.tasksRepository)
    private val singleTaskMapper = TaskViewMapper()

    val provideMainViewModel = SharedViewModel(getTaskByIdUseCase, setTaskResolvedUseCase, singleTaskMapper)

}