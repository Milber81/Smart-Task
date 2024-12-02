package com.smart.task.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smart.task.databinding.CityItemBinding
import com.smart.task.ui.helpers.getImageUrl
import com.smart.task.ui.helpers.loadIcon

class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = CityItemBinding.bind(view)

    fun render(
        viewItem: CityViewItem,
        onClickListener: (CityViewItem) -> Unit,
        onRemoveItemListener: (CityViewItem) -> Unit
    ) {
        binding.cityAndCountry.text = viewItem.name
        binding.temperature.text = viewItem.temperature

        val url = getImageUrl(viewItem.icon)
        loadIcon(url, binding.imageView)

        itemView.setOnClickListener { onClickListener(viewItem) }
        binding.imgDelete.setOnClickListener { onRemoveItemListener(viewItem) }
    }

    fun updateTextAndIcon( viewItem: CityViewItem){
        binding.temperature.text = viewItem.temperature

        val url = getImageUrl(viewItem.icon)
        loadIcon(url, binding.imageView)
    }
}
