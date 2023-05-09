package com.ada.storeappharrison.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ada.storeappharrison.storage.room.dao.ProductDao
import com.ada.storeappharrison.storage.room.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
}