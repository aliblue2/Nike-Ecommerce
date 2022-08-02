package com.alireza.nikeiran.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.data.source.ProductLocalDataSource

@Database(entities = [Product::class] , version = 1 , exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract fun productDao() : ProductLocalDataSource
}