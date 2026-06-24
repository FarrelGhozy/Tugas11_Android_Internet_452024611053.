package com.example.tugas11.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.tugas11.R

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    if (url.isNullOrBlank()) {
        view.setImageResource(R.drawable.ic_error)
        return
    }
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_error)
        .centerCrop()
        .into(view)
}
