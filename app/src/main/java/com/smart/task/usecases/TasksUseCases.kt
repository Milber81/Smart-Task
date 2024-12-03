package com.smart.task.usecases

import com.smart.task.domain.Task
import com.smart.task.domain.repositories.TaskRepository
import java.util.Calendar
import java.util.Date

class GetAllTasksUseCase(private val repository: TaskRepository){
    suspend operator fun invoke(): List<Task>{
        return repository.getAll()
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

        println("ooooo ------->: ${Date(dayStartMillis)}")
        println("ooooo -------> tooooo: ${Date(dayEndMillis)}")

        return repository.getAll().filter { task ->
            task.targetDate in dayStartMillis..dayEndMillis
        }
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

