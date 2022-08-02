package com.alireza.nikeiran.data.source.cart

import com.alireza.nikeiran.data.MessageResponse
import io.reactivex.Single

interface CartRemoteDataSource {
    fun addToCart(productId:Int): Single<AddToCartResponse>

    fun removeFromCart(cartItemId:Int) : Single<MessageResponse>

    fun getAll(): Single<CartResponse>

    fun changeCountItem(cartItemId: Int, count:Int) : Single<AddToCartResponse>

    fun getCartItemCount(): Single<CartItemCount>
}