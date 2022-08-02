package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.MessageResponse
import com.alireza.nikeiran.data.source.cart.*
import io.reactivex.Single

class CartRepoImpl(val cartRemoteDataSource: CartRemoteDataSource) :CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
       return cartRemoteDataSource.addToCart(productId)
    }

    override fun removeFromCart(cartItemId: Int): Single<MessageResponse> =cartRemoteDataSource.removeFromCart(cartItemId)

    override fun getAll(): Single<CartResponse> = cartRemoteDataSource.getAll()

    override fun changeCountItem(cartItemId: Int, count: Int): Single<AddToCartResponse> = cartRemoteDataSource.changeCountItem(cartItemId, count)

    override fun getCartItemCount(): Single<CartItemCount> = cartRemoteDataSource.getCartItemCount()
}