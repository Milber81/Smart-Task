package com.smart.task.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.task.R

class TasksAdapter(
    private var taskList: List<TaskViewItem>? = null,
    private val onClickListener: (TaskViewItem) -> Unit) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskItem = taskList?.get(position)

        if (taskItem != null) {
            holder.render(taskItem, onClickListener)
        }
    }

    override fun getItemCount(): Int = taskList?.size ?: 0

    fun swapData(items: List<TaskViewItem>){
        taskList = items
        notifyDataSetChanged()
    }

    fun updateTaskViewItem(viewItem: TaskViewItem) {
        taskList?.let { currentList ->
            val index = currentList.indexOfFirst { it.id == viewItem.id } // Use unique ID to find the item
            if (index != -1) {
                val updatedList = currentList.toMutableList()
                updatedList[index] = viewItem
                taskList = updatedList
                notifyItemChanged(index) // Notify only the specific item that was changed
            }
        }
    }
}
