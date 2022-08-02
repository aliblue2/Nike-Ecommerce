package com.alireza.nikeiran.feature.main

import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.data.repo.CartRepository
import com.alireza.nikeiran.data.source.cart.CartItemCount
import com.alireza.nikeiran.data.source.user.TokenContainer
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) :NikeViewModel(){
    fun getCartItemCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemCount().subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }

                })
        }
    }
}