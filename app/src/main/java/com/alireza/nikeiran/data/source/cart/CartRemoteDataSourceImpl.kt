package com.alireza.nikeiran.data.source.cart

import com.alireza.nikeiran.data.MessageResponse
import com.alireza.nikeiran.serviceHttp.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSourceImpl(val apiService: ApiService) : CartRemoteDataSource{
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
        return apiService.addToCart(JsonObject().apply {
            addProperty("product_id", productId)
        })
    }

    override fun removeFromCart(cartItemId: Int): Single<MessageResponse> = apiService.removeFromCart(JsonObject().apply {
        addProperty("cart_item_id",cartItemId)
    })

    override fun getAll(): Single<CartResponse> = apiService.getCartList()

    override fun changeCountItem(cartItemId: Int, count: Int): Single<AddToCartResponse> =apiService.changeCount(JsonObject().apply {
        addProperty("cart_item_id",cartItemId)
        addProperty("count",count)
    })

    override fun getCartItemCount(): Single<CartItemCount> = apiService.getCartItemCount()
}