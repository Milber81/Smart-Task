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


class SetTaskResolvedUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: String) {
        // Retrieve the task by its ID
        val taskOfInterest = repository.getById(taskId)

        if (taskOfInterest != null) {
            // Update the task with the resolved status
            val updatedTask = taskOfInterest.copy(_status = Task.RESOLVED)

            // Save the updated task back to the repository
            repository.updateTask(updatedTask)
        } else {
            // Handle the case where the task is not found
            throw IllegalArgumentException("Task with ID $taskId not found")
        }
    }
}


class AddTaskCommentUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(comment: String, taskId: String) {
        // Retrieve the task by its ID
        val taskOfInterest = repository.getById(taskId)

        if (taskOfInterest != null) {
            // Update the task with the new comment
            val updatedTask = taskOfInterest.copy(comment = comment)

            // Save the updated task back to the repository
            repository.updateTask(updatedTask)
        } else {
            // Handle the case where the task is not found (optional)
            throw IllegalArgumentException("Task with ID $taskId not found")
        }
    }
}

