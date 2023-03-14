package com.example.onlineshop.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

object LatestItemSerializer : KSerializer<LatestItem> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("latest_item") {
        element<String>("category")
        element<String>("image_url")
        element<String>("name")
        element<Int>("price")
    }

    override fun serialize(encoder: Encoder, value: LatestItem) { }

    override fun deserialize(decoder: Decoder): LatestItem = decoder.decodeStructure(descriptor) {
        var category: String? = null
        var imageUrl: String? = null
        var name: String? = null
        var price: Double? = null

        loop@ while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break@loop
                0 -> category = decodeStringElement(descriptor, index = 0)
                1 -> imageUrl = decodeStringElement(descriptor, index = 1)
                2 -> name = decodeStringElement(descriptor, index = 2)
                3 -> price = decodeDoubleElement(descriptor, index = 3)
                else -> throw SerializationException("Unexpected index $index")
            }
        }
        if (category == null || imageUrl == null || name == null || price == null) {
            throw SerializationException("Someone field is null")
        }
        return@decodeStructure LatestItem(category, imageUrl, name, price)
    }
}

@Serializable(with = LatestItemSerializer::class)
data class LatestItem(
    val category: String,
    val image_url: String,
    val name: String,
    val price: Double
): AdapterListItem