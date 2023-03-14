package com.example.onlineshop.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


object ColorItemSerializer : KSerializer<ColorItem> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ColorItem", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ColorItem) {}

    override fun deserialize(decoder: Decoder): ColorItem {
        val url = decoder.decodeString()
        return ColorItem(url)
    }
}

@Serializable(with = ColorItemSerializer::class)
data class ColorItem(val color: String, var isSelected: Boolean = false)