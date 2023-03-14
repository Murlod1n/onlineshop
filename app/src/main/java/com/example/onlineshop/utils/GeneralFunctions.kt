package com.example.onlineshop.utils


import android.widget.ImageView
import com.squareup.picasso.Picasso



fun loadImage(imageView: ImageView, url: String) {
    Picasso
        .get()
        .load(url)
        .fit()
        .centerCrop()
        .into(imageView)
}