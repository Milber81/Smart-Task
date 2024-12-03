package com.smart.task.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smart.task.databinding.CityItemBinding

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = CityItemBinding.bind(view)

    fun render(
        viewItem: TaskViewItem,
        onClickListener: (TaskViewItem) -> Unit,
        onRemoveItemListener: (TaskViewItem) -> Unit
    ) {
        binding.cityAndCountry.text = viewItem.name
        binding.temperature.text = viewItem.temperature

        itemView.setOnClickListener { onClickListener(viewItem) }
        binding.imgDelete.setOnClickListener { onRemoveItemListener(viewItem) }
    }
}
