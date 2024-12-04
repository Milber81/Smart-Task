package com.smart.task.ui.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smart.task.R
import com.smart.task.databinding.TaskItemBinding

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = TaskItemBinding.bind(view)

    fun render(
        viewItem: TaskViewItem,
        onClickListener: (TaskViewItem) -> Unit,
        onRemoveItemListener: (TaskViewItem) -> Unit
    ) {
        binding.txtTitle.text = viewItem.title
        val dueDate = binding.root.findViewById<TextView>(R.id.dueDate)
        val daysLeft = binding.root.findViewById<TextView>(R.id.daysLeft)
        dueDate.text = viewItem.date
        daysLeft.text = viewItem.daysOffset

        itemView.setOnClickListener { onClickListener(viewItem) }
    }
}
