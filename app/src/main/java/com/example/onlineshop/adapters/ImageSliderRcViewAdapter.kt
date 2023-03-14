package com.example.onlineshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.domain.models.SliderItem
import com.example.onlineshop.databinding.MiniSliderItemBinding
import com.example.onlineshop.utils.loadImage


class ImageSliderRcViewAdapter(private val urlList: List<SliderItem>, val selectImage: (Int) -> Unit) :

    RecyclerView.Adapter<ImageSliderRcViewAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(private val binding: MiniSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: SliderItem, position: Int) {
            if (item.isSelected) {
                binding.miniSliderItemCard.layoutParams.height = 156
                binding.miniSliderItemCard.layoutParams.width = 240
            }
            loadImage(binding.image, item.url)
            binding.image.setOnClickListener {
                selectImage(position)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSliderViewHolder {
        val binding = MiniSliderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ImageSliderViewHolder(binding)
    }

    override fun getItemCount() = urlList.size

    override fun onBindViewHolder(
        holder: ImageSliderRcViewAdapter.ImageSliderViewHolder,
        position: Int
    ) {
       holder.setData(urlList[position], position)
    }

}