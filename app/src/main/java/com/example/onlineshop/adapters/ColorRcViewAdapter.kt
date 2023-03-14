package com.example.onlineshop.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import com.example.onlineshop.domain.models.ColorItem
import com.example.onlineshop.databinding.ColorItemBinding


class ColorRcViewAdapter(private val colorList: List<ColorItem>, val selectColor: (Int) -> Unit) :
    RecyclerView.Adapter<ColorRcViewAdapter.ColorRcViewHolder>() {

    inner class ColorRcViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {

        private val binding = ColorItemBinding.bind(view)

        fun setData(item: ColorItem, position: Int) {
            if (item.isSelected) {
                binding.colorItem.strokeWidth = 5
                binding.colorItem.strokeColor = view.context.resources.getColor(R.color.button_color)
                binding.colorItem.isEnabled = false
            }
            binding.colorItem.setOnClickListener {
                selectColor(position)
            }
            binding.colorItem.setCardBackgroundColor(Color.parseColor(item.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorRcViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)

        return ColorRcViewHolder(view)
    }

    override fun getItemCount() = colorList.size

    override fun onBindViewHolder(holder: ColorRcViewHolder, position: Int) {
        holder.setData(colorList[position], position)
    }

}