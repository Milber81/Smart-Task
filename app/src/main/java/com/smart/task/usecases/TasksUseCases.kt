package com.smart.task.usecases

import com.smart.task.domain.Task
import com.smart.task.domain.repositories.TaskRepository
import java.util.Calendar

class GetAllTasksUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(): List<Task>{
        return repository.getAll()
    }
}

class GetTaskByIdUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(id: String): Task?{
        return repository.getById(id)
    }
}

class GetAllTasksForDayUseCase(private val repository: TaskRepository) {

    private var currentDay = System.currentTimeMillis()

    fun setDay(day: Long) {
        currentDay = day
    }

    fun getDay(): Long = currentDay

    suspend operator fun invoke(): List<Task> {
        val dayStartMillis = getDayStartMillis(currentDay)
        val dayEndMillis = dayStartMillis + (24 * 60 * 60 * 1000) - 1 // End of the day

        return repository.getAll().filter { task ->
            task.targetDate in dayStartMillis..dayEndMillis
        }.sortedBy { it.priority }
    }

    private fun getDayStartMillis(date: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}


class SetTaskStatusUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: String, status: Int) {
        val taskOfInterest = repository.getById(taskId)

        if (taskOfInterest != null) {
            val updatedTask = taskOfInterest.copy(_status = status)

            repository.updateTask(updatedTask)
        } else {
            throw IllegalArgumentException("Task with ID $taskId not found")
        }
    }
}


class AddTaskCommentUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(comment: String, status: Int, taskId: String) {
        val taskOfInterest = repository.getById(taskId)

        if (taskOfInterest != null) {
            val updatedTask = taskOfInterest.copy(_status = status, comment = comment)

            repository.updateTask(updatedTask)
        } else {
            throw IllegalArgumentException("Task with ID $taskId not found")
        }
    }
}

