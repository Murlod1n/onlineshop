package com.example.onlineshop.domain.models

data class CategoryItemList(
    override val title: String,
    override val list: List<CategoryItem>
): AdapterList