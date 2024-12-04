package com.smart.task.domain

import kotlinx.serialization.Serializable

typealias Comment = String

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val targetDate: Long,
    val dueDate: Long?,
    val priority: Int,
    private var _status: Int,
    val comment: Comment? = null
) {
    var status: Int
        get() = _status
        set(value) {
            require(value in VALID_STATUSES) { "Invalid status: $value" }
            _status = value
        }

    companion object {
        const val UNRESOLVED = 0
        const val RESOLVED = 1
        const val CANT_RESOLVE = 2

        val VALID_STATUSES = setOf(UNRESOLVED, RESOLVED, CANT_RESOLVE)
    }
}



