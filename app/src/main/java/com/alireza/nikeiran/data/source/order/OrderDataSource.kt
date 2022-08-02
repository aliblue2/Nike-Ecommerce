package com.alireza.nikeiran.data.source.order

import com.alireza.nikeiran.data.source.order.PaymentResult
import com.alireza.nikeiran.data.source.order.SubmitOrderResponse
import com.alireza.nikeiran.data.source.orderHistory.OrderHistoryItem
import io.reactivex.Single

interface OrderDataSource {

    fun submitOrder(firstName : String ,
                    lastName : String ,
                    postalCode : String ,
                    phoneNumber : String ,
                    address:String ,
                    paymentMethod:String) :Single<SubmitOrderResponse>

    fun orderResult(orderId :Int) : Single<PaymentResult>

    fun orderHistory():Single<List<OrderHistoryItem>>

}