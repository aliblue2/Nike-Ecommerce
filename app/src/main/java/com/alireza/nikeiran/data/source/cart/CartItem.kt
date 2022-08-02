package com.alireza.nikeiran.data.source.cart

import com.alireza.nikeiran.data.source.Product

data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var changeCountProgressBarIsVisible : Boolean = false
)