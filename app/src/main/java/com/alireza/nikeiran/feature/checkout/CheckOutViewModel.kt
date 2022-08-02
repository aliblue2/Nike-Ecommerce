package com.alireza.nikeiran.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.repo.OrderRepository
import com.alireza.nikeiran.data.source.order.PaymentResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CheckOutViewModel(orderId:Int , orderRepository: OrderRepository) :NikeViewModel() {
    val paymentLiveData = MutableLiveData<PaymentResult>()
    init {
        orderRepository.orderResult(orderId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object :NikeSingleObserver<PaymentResult>(compositeDisposable){
                override fun onSuccess(t: PaymentResult) {
                    paymentLiveData.value = t
                }
            })
    }
}