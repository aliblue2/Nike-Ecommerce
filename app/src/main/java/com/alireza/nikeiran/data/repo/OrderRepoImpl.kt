package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.order.OrderDataSource
import com.alireza.nikeiran.data.source.order.PaymentResult
import com.alireza.nikeiran.data.source.order.SubmitOrderResponse
import com.alireza.nikeiran.data.source.orderHistory.OrderHistoryItem
import io.reactivex.Single

class OrderRepoImpl(val orderDataSource: OrderDataSource) : OrderRepository {
    override fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ): Single<SubmitOrderResponse> {
        return orderDataSource.submitOrder(firstName,lastName,postalCode,phoneNumber,address,paymentMethod)
    }

    override fun orderResult(orderId: Int): Single<PaymentResult> {
        return orderDataSource.orderResult(orderId)
    }

    override fun orderHistory(): Single<List<OrderHistoryItem>> {
        return orderDataSource.orderHistory()
    }
}