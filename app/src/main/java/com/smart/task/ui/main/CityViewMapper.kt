package com.smart.task.ui.main

data class TaskViewItem(
    val id: Int,
    val name: String,
    val icon: String,
    val temperature: String
) {
    // Override equals and hashCode to compare content rather than reference
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskViewItem
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}

