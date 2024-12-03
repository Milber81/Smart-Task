package com.smart.task.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.task.R

class TasksAdapter(
    private var taskList: MutableList<TaskViewItem>,
    private val onClickListener: (TaskViewItem) -> Unit,
    private val onItemRemoveClickListener: (TaskViewItem) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.city_item, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val itemCityInfo = taskList[position]

        holder.render(itemCityInfo, onClickListener, onItemRemoveClickListener)
    }

    override fun getItemCount(): Int = taskList.size

    fun updateCity(viewItem: TaskViewItem) {
        val index = taskList.indexOf(viewItem)
        if (index != -1) {
            taskList[index] = viewItem
            notifyItemChanged(index, true) // Notify only the specific item that was changed
        } else {
            taskList.add(viewItem)
            notifyDataSetChanged()
        }
    }

    fun removeCity(viewItem: TaskViewItem){
        val index = taskList.indexOf(viewItem)
        if (index != -1) {
            taskList.remove(viewItem)
            notifyItemRemoved(index) // Notify only the specific item is removed
        }
    }
}
