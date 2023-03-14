package com.example.onlineshop.domain.models

import kotlinx.serialization.*


@Serializable
data class LatestItemList(
    override val title: String = "Latest",
    @SerialName("latest")
    override val list: List<LatestItem>
): AdapterList