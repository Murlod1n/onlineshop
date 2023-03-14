package com.example.onlineshop.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


object SliderItemSerializer : KSerializer<SliderItem> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SliderItem", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SliderItem) { }

    override fun deserialize(decoder: Decoder): SliderItem {
        val url = decoder.decodeString()
        return SliderItem(url)
    }
}


@Serializable(with = SliderItemSerializer::class)
data class SliderItem(
    val url: String,
    var isSelected: Boolean = false)