package com.alireza.nikeiran.feature.checkout

import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.data.repo.OrderRepository
import com.alireza.nikeiran.data.source.order.SubmitOrderResponse
import io.reactivex.Single
const val ORDER_PAYMENT_METHOD_COD = "cash_on_delivery"
const val ORDER_PAYMENT_METHOD_ONLINE = "online"
class OrderViewModel(private val orderRepository: OrderRepository) :NikeViewModel() {

    fun submitOrder(firstName:String , lastName:String , postalCode:String ,
     phoneNumber :String , address:String , paymentMethod:String) :Single<SubmitOrderResponse>{
        return orderRepository.submitOrder(firstName,lastName,postalCode,phoneNumber,address,paymentMethod)
    }

}