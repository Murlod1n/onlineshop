package com.example.onlineshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.domain.models.SliderItem
import com.example.onlineshop.databinding.SliderItemBinding
import com.example.onlineshop.utils.loadImage

class ImageViewPagerAdapter(private val imageUrlList: List<SliderItem>) :
    RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerViewHolder>() {


    inner class ViewPagerViewHolder(private val binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(imageUrl: String) {
            loadImage(binding.sliderImage, imageUrl)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = SliderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount() = imageUrlList.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(imageUrlList[position].url)
    }

}