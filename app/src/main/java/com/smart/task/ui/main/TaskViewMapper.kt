package com.smart.task.ui.main

import com.smart.task.R
import com.smart.task.base.ListMapper
import com.smart.task.base.SingleMapper
import com.smart.task.domain.Task
import java.text.SimpleDateFormat
import java.util.Locale

data class TaskViewItem(
    val id: String,
    val title: String,
    val date: String,
    val daysOffset: String,
    val description: String? = null,
    val color: Int,
    val statusIcon: Int,
    val statusText: String? = null,
    val status: Int? = Task.UNRESOLVED,
    val comment: String? = null
)

class TasksViewMapper : ListMapper<Task, TaskViewItem> {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override suspend fun map(items: List<Task>): List<TaskViewItem> {
        return items.map {
            TaskViewItem(
                it.id,
                it.title,
                sdf.format(it.targetDate),
                ((it.dueDate?.minus(it.targetDate))?.div((1000 * 60 * 60 * 24)))?.toInt()
                    .toString(),
                color = when (it.status) {
                    Task.UNRESOLVED -> R.color.primary
                    Task.RESOLVED -> R.color.green
                    Task.CANT_RESOLVE -> R.color.main_text
                    else -> -1
                },
                statusIcon = when (it.status) {
                    Task.UNRESOLVED -> -1
                    Task.RESOLVED -> R.drawable.btn_resolved
                    Task.CANT_RESOLVE -> R.drawable.btn_unresolved
                    else -> -1
                },
            )
        }
    }
}

class TaskViewMapper : SingleMapper<Task, TaskViewItem> {

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override suspend fun map(item: Task): TaskViewItem {
        return TaskViewItem(
            item.id, item.title, sdf.format(item.targetDate),
            ((item.dueDate?.minus(item.targetDate))?.div((1000 * 60 * 60 * 24)))?.toInt()
                .toString(),
            item.description,
            color = when (item.status) {
                Task.UNRESOLVED -> R.color.primary
                Task.RESOLVED -> R.color.green
                Task.CANT_RESOLVE -> R.color.main_text
                else -> -1
            },
            when (item.status) {
                Task.UNRESOLVED -> -1
                Task.RESOLVED -> R.drawable.btn_resolved
                Task.CANT_RESOLVE -> R.drawable.btn_unresolved
                else -> -1
            },
            when (item.status) {
                Task.UNRESOLVED -> "Unresolved"
                Task.RESOLVED -> "Resolved"
                Task.CANT_RESOLVE -> "Can't resolve"
                else -> "Unresolved"
            },
            item.status,
            item.comment
        )
    }
}
