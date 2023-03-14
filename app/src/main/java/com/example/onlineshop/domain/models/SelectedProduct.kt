package com.example.onlineshop.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




@Serializable
data class SelectedProduct(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("number_of_reviews")
    val numberOfReviews: Int,
    @SerialName("price")
    val price: Double,
    @SerialName("colors")
    val colors: MutableList<ColorItem>,
    @SerialName("image_urls")
    val imagesUrls: MutableList<SliderItem>,
    var isSelected: Boolean = false
) {

    fun updateImageField(position: Int, isSelected: Boolean) {
        imagesUrls[position].isSelected = isSelected
    }

    fun updateColorField(position: Int, isSelected: Boolean) {
        colors[position].isSelected = isSelected
    }

    fun updateSelectedField() {
        isSelected = !isSelected
    }
}
