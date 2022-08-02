package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.data.source.ProductDataSource
import com.alireza.nikeiran.data.source.ProductLocalDataSource
import com.alireza.nikeiran.data.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepoImpl(val remoteDataSource: ProductDataSource , val localDataSource: ProductDataSource) :ProductRepository{

    override fun getProductList(sort:Int): Single<List<Product>> {
        return localDataSource.getFavoriteProduct().flatMap { favoriteProduct ->
            remoteDataSource.getProductList(sort).doOnSuccess {
                val favoriteProductId = favoriteProduct.map {
                    it.id
                }
                it.forEach { product ->
                    if (favoriteProductId.contains(product.id))
                        product.isFavorite = true
                }
            }
        }
    }


    override fun getFavoriteProduct(): Single<List<Product>> {
        return localDataSource.getFavoriteProduct()
    }

    override fun addToFavoriteProduct(product: Product): Completable {
        return localDataSource.addToFavoriteProduct(product)
    }

    override fun deleteFromFavoriteProduct(product: Product): Completable {
        return localDataSource.deleteFromFavoriteProduct(product)
    }

}