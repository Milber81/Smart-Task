package com.smart.task.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smart.task.databinding.TaskItemBinding

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = TaskItemBinding.bind(view)

    fun render(
        viewItem: TaskViewItem,
        onClickListener: (TaskViewItem) -> Unit,
        onRemoveItemListener: (TaskViewItem) -> Unit
    ) {
        binding.title.text = viewItem.title
        binding.dueDate.text = viewItem.date
        binding.daysLeft.text = viewItem.daysOffset

        itemView.setOnClickListener { onClickListener(viewItem) }
    }
}
