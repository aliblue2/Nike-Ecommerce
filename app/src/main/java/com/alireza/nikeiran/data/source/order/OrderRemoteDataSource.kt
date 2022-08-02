package com.alireza.nikeiran.data.source.order

import com.alireza.nikeiran.data.source.order.PaymentResult
import com.alireza.nikeiran.data.source.order.SubmitOrderResponse
import com.alireza.nikeiran.data.source.orderHistory.OrderHistoryItem
import com.alireza.nikeiran.serviceHttp.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource (val apiService: ApiService): OrderDataSource {
    override fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ): Single<SubmitOrderResponse> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name" , firstName)
            addProperty("last_name" ,lastName)
            addProperty("postal_code" , postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address",address)
            addProperty("payment_method",paymentMethod)
        })
    }

    override fun orderResult(orderId: Int): Single<PaymentResult> {
        return apiService.orderResult(orderId)
    }

    override fun orderHistory(): Single<List<OrderHistoryItem>> {
        return apiService.orderHistory()
    }
}