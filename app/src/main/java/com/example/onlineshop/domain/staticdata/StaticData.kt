package com.example.onlineshop.domain.staticdata

import com.example.onlineshop.R
import com.example.onlineshop.domain.models.BrandItem
import com.example.onlineshop.domain.models.CategoryItem
import com.example.onlineshop.domain.models.FlashSaleItem
import com.example.onlineshop.domain.models.LatestItem

object StaticData {
    fun getCategoriesData() = listOf(
        CategoryItem(1, "Phones", R.drawable.phone_svg),
        CategoryItem(2, "Headphones", R.drawable.headphone_svg),
        CategoryItem(3, "Games", R.drawable.gamepad_svg),
        CategoryItem(4, "Cars", R.drawable.car_svg),
        CategoryItem(5, "Furniture", R.drawable.bed_svg),
        CategoryItem(6, "Kids", R.drawable.robot_svg)
    )

    fun getLoadingLatestItems() = listOf(
        LatestItem(category = "", image_url = "", name = "", price = 0.0),
        LatestItem(category = "", image_url = "", name = "", price = 0.0),
        LatestItem(category = "", image_url = "", name = "", price = 0.0)
    )

    fun getLoadingFlashSaleItems() = listOf(
        FlashSaleItem("", 0.1, "", "", 0.1),
        FlashSaleItem("", 0.1, "", "", 0.1),
    )

    fun getBrandItems() = listOf(
        BrandItem(
            "BMW",
            "https://st4.depositphotos.com/2365037/19922/i/600/depositphotos_199229328-stock-photo-crozon-france-may-29th-2018.jpg"
        ),
        BrandItem(
            "Play Station",
            "https://i.pinimg.com/originals/22/fd/e0/22fde06d09822a00fa6f15179aa7edfd.jpg"
        ),
        BrandItem(
            "Nike",
            "https://images.footlocker.com/content/dam/final/footlocker/site/backpages/20201221-fl-nike-brand-page-m-asp.jpg"
        ),
        BrandItem(
            "Apple",
            "https://w0.peakpx.com/wallpaper/40/1017/HD-wallpaper-apple-iphone-apple-logo.jpg"
        )
    )


}