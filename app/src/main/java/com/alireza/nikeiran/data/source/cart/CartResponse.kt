package com.alireza.nikeiran.data.source.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)
@Parcelize
data class PurchaseDetail(
    var payable_price: String,
    var shipping_cost: String,
    var total_price: String) : Parcelable