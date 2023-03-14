package com.example.onlineshop.domain.models

data class BrandItemList(
    override val title: String,
    override val list: List<BrandItem>
): AdapterList