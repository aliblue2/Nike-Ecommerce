package com.alireza.nikeiran.data.source

import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProductList(sort:Int): Single<List<Product>>



    fun getFavoriteProduct(): Single<List<Product>>

    fun addToFavoriteProduct(product: Product): Completable

    fun deleteFromFavoriteProduct(product: Product): Completable
}