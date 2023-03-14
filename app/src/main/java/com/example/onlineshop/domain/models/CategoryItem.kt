package com.example.onlineshop.domain.models

data class CategoryItem(
    val id: Int,
    val title: String,
    val resourceId: Int
) : AdapterListItem
