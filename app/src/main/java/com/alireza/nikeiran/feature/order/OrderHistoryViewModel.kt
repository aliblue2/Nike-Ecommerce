package com.alireza.nikeiran.feature.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.repo.OrderRepository
import com.alireza.nikeiran.data.source.orderHistory.OrderHistoryItem

class OrderHistoryViewModel(orderRepository: OrderRepository) :NikeViewModel(){
    private val _orderHistory = MutableLiveData<List<OrderHistoryItem>>()
    val orderItems :LiveData<List<OrderHistoryItem>>
    get() = _orderHistory
    init {
        progressIndicatorLiveData.value = true
        orderRepository.orderHistory().async()
            .doFinally { progressIndicatorLiveData.value = false }
            .subscribe(object :NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    _orderHistory.value = t
                }
            })
    }
}