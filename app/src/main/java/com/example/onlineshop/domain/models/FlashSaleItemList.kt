package com.example.onlineshop.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FlashSaleItemList(
    override val title: String = "Flash Sale",
    @SerialName("flash_sale")
    override val list: List<FlashSaleItem>
) : AdapterList