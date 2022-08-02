package com.alireza.nikeiran.data.source.orderHistory

data class OrderHistoryItem(
    val id: Int,
    val payable: Int,
    val order_items: List<OrderItem>,

    )