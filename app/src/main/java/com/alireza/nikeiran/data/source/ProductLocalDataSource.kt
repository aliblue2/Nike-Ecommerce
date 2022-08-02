package com.alireza.nikeiran.data.source

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
@Dao
interface ProductLocalDataSource :ProductDataSource {
    override fun getProductList(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM favorite_tbl")
    override fun getFavoriteProduct(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavoriteProduct(product: Product): Completable

    @Delete
    override fun deleteFromFavoriteProduct(product: Product): Completable
}