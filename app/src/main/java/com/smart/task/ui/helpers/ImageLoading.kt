package com.smart.task.ui.helpers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.smart.task.R

fun loadIcon(url: String, imageView: ImageView) {
    Glide.with(imageView).load(url).error(R.drawable.logo)
        .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(imageView)
}