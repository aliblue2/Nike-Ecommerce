package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.MessageResponse
import com.alireza.nikeiran.data.source.cart.AddToCartResponse
import com.alireza.nikeiran.data.source.cart.CartItemCount
import com.alireza.nikeiran.data.source.cart.CartResponse
import io.reactivex.Single

interface CartRepository {
    fun addToCart(productId:Int):Single<AddToCartResponse>

    fun removeFromCart(cartItemId:Int) : Single<MessageResponse>

    fun getAll():Single<CartResponse>

    fun changeCountItem(cartItemId: Int, count:Int) : Single<AddToCartResponse>

    fun getCartItemCount():Single<CartItemCount>

}