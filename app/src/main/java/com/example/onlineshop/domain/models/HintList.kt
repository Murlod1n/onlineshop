package com.example.onlineshop.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HintList(
    @SerialName("words")
    val words: List<String>
)