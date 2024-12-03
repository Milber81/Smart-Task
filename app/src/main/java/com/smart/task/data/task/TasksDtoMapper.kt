package com.smart.task.data.task

import com.smart.task.base.ListMapper
import com.smart.task.data.task.models.TaskDto
import com.smart.task.domain.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TasksDtoMapper : ListMapper<TaskDto, Task> {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun map(items: List<TaskDto>): List<Task> = items.map {
        Task(
            id = it.id,
            title = it.title,
            description = it.description,
            targetDate = it.targetDate.let { date -> sdf.parse(date)?.time }
                ?: throw IllegalArgumentException("Invalid Target Date"),
            dueDate = it.dueDate?.let { date -> sdf.parse(date)?.time },
            priority = it.priority
        )
    }
}
