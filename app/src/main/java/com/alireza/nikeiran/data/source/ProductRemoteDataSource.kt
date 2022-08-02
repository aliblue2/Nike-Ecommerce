package com.alireza.nikeiran.data.source

import com.alireza.nikeiran.serviceHttp.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) :ProductDataSource {
    override fun getProductList(sort:Int): Single<List<Product>> {
    return apiService.getProductList(sort.toString())
    }


    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavoriteProduct(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavoriteProduct(product: Product): Completable {
        TODO("Not yet implemented")
    }
}