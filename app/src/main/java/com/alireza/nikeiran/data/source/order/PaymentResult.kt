package com.alireza.nikeiran.data.source.order

data class PaymentResult(
    val payable_price: Int,
    val payment_status: String,
    val purchase_success: Boolean
)