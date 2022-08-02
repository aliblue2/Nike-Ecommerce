package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProductList(sort:Int):Single<List<Product>>

    fun getFavoriteProduct():Single<List<Product>>

    fun addToFavoriteProduct(product: Product):Completable

    fun deleteFromFavoriteProduct(product: Product):Completable
}