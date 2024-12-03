package com.smart.task.ui.main

import com.smart.task.base.ListMapper
import com.smart.task.domain.Task
import java.text.SimpleDateFormat
import java.util.Locale

data class TaskViewItem(
    val id: String,
    val title: String,
    val date: String,
    val daysOffset: String
) {
    // Override equals and hashCode to compare content rather than reference
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskViewItem
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + daysOffset.hashCode()
        return result
    }
}


class TaskViewMapper : ListMapper<Task, TaskViewItem> {

    private val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())

    override suspend fun map(items: List<Task>): List<TaskViewItem> {
        return items.map {
            TaskViewItem(
                it.id, it.title, sdf.format(it.targetDate),
                ((it.dueDate?.minus(it.targetDate))?.div((1000 * 60 * 60 * 24)))?.toInt().toString()
            )
        }
    }
}
