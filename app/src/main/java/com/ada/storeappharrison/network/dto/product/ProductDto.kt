package com.ada.storeappharrison.network.dto.product

data class ProductDto(
    val category: String,
    val description: String,
    val imageUrl: String,
    val name: String,
    val price: Double,
    val tags: List<String>
)