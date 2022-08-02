package com.alireza.nikeiran.feature.cart

import android.util.EventLog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.EmptyState
import com.alireza.nikeiran.data.repo.CartRepoImpl
import com.alireza.nikeiran.data.repo.CartRepository
import com.alireza.nikeiran.data.source.cart.CartItem
import com.alireza.nikeiran.data.source.cart.CartItemCount
import com.alireza.nikeiran.data.source.cart.CartResponse
import com.alireza.nikeiran.data.source.cart.PurchaseDetail
import com.alireza.nikeiran.data.source.user.TokenContainer
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus

class CartViewModel(val cartRepository: CartRepository) :NikeViewModel() {
    val cartItemLiveData=MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()
    fun getCartItems() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressIndicatorLiveData.value = true
            emptyStateLiveData.value = EmptyState(false)
            cartRepository.getAll().async()
                .doAfterSuccess { progressIndicatorLiveData.value = false
                }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.payable_price.toString(),
                                    t.shipping_cost.toString(),
                                    t.total_price.toString())
                        } else
                            emptyStateLiveData.value = EmptyState(true,R.string.cartEmptyStateDefault,false,true)
                    }
                })
        }else
            emptyStateLiveData.value = EmptyState(true,R.string.cartEmptyStateLoginRequired,true)
    }


    fun removeFromCart(cartItem: CartItem):Completable{
        return cartRepository.removeFromCart(cartItem.cart_item_id)
            .doAfterSuccess{
                calculateAndPublishedPurchaseDetail()
                cartItemLiveData.value?.let {
                    if (it.isEmpty())
                        emptyStateLiveData.postValue(EmptyState(true,R.string.cartEmptyStateDefault,false,true))
                }

                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count -= cartItem.count
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem):Completable{
        return cartRepository.changeCountItem(cartItem.cart_item_id,++cartItem.count)
            .doOnSuccess{ calculateAndPublishedPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count += 1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()
    }

    fun decreaseCartItemCount(cartItem: CartItem):Completable{
        return cartRepository.changeCountItem(cartItem.cart_item_id,--cartItem.count)
            .doAfterSuccess{ calculateAndPublishedPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count -= 1
                    EventBus.getDefault().postSticky(it)
                }}
            .ignoreElement()
    }

    fun refresh(){
        return getCartItems()
    }

    fun calculateAndPublishedPurchaseDetail(){
        cartItemLiveData.value?.let { cartItem ->
            purchaseDetailLiveData.value?.let { purchase ->
                var totalPrice=0
                var payablePrice= 0
                cartItem.forEach{
                    totalPrice = it.product.price * it.count
                    payablePrice = (it.product.price - it.product.discount) * it.count
                }
                purchase.total_price = totalPrice.toString()
                purchase.payable_price = payablePrice.toString()
            }
        }
    }
}