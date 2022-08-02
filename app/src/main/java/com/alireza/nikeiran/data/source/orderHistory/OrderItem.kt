package com.alireza.nikeiran.data.source.orderHistory

import com.alireza.nikeiran.data.source.Product

data class OrderItem(
    val count: Int,
    val discount: Int,
    val id: Int,
    val order_id: Int,
    val price: Int,
    val product: Product,
    val product_id: Int
)