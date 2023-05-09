package com.ada.storeappharrison.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") val productId: Int? = null,
    val category: String?,
    val description: String?,
    @ColumnInfo(name = "image_url")val imageUrl: String?,
    val name: String?,
    val price: Double?
){
    constructor(
        category: String?,
        description: String?,
        imageUrl: String?,
        name: String?,
        price: Double?
    ): this(
        null,
        category,
        description,
        imageUrl,
        name,
        price
    )
}
