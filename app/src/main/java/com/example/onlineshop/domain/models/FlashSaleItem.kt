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

object FlashSaleItemSerializer : KSerializer<FlashSaleItem> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("flash_sale") {
        element<String>("category")
        element<Double>("discount")
        element<String>("image_url")
        element<String>("name")
        element<Int>("price")
    }

    override fun serialize(encoder: Encoder, value: FlashSaleItem) { }

    override fun deserialize(decoder: Decoder): FlashSaleItem = decoder.decodeStructure(descriptor) {
        var category: String? = null
        var discount: Double? = null
        var imageUrl: String? = null
        var name: String? = null
        var price: Double? = null

        loop@ while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break@loop
                0 -> category = decodeStringElement(descriptor, index = 0)
                1 -> discount = decodeDoubleElement(descriptor, index = 1)
                2 -> imageUrl = decodeStringElement(descriptor, index = 2)
                3 -> name = decodeStringElement(descriptor, index = 3)
                4 -> price = decodeDoubleElement(descriptor, index = 4)
                else -> throw SerializationException("Unexpected index $index")
            }
        }
        if (category == null || discount == null || imageUrl == null || name == null || price == null) {
            throw SerializationException("Someone field is null")
        }
        return@decodeStructure FlashSaleItem(category, discount, imageUrl, name, price)
    }
}

@Serializable(with = FlashSaleItemSerializer::class)
data class FlashSaleItem(
    val category: String,
    val discount: Double,
    val image_url: String,
    val name: String,
    val price: Double
): AdapterListItem