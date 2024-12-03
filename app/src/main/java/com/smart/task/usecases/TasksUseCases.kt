package com.smart.task.usecases

import com.smart.task.domain.Task
import com.smart.task.domain.repositories.TaskRepository
import java.util.Calendar

class GetAllTasksUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(): List<Task>{
        return repository.getAll()
    }
}

class GetAllTasksForTodayUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(): List<Task> {
        val todayStartMillis = getTodayStartMillis()
        val todayEndMillis = todayStartMillis + (24 * 60 * 60 * 1000) - 1 // End of the day

        return repository.getAll().filter { task ->
            task.targetDate in todayStartMillis..todayEndMillis
        }
    }

    private fun getTodayStartMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}

