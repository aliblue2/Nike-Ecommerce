package com.alireza.nikeiran.data.source.order

data class SubmitOrderResponse(
    val bank_gateway_url: String,
    val order_id: Int
)