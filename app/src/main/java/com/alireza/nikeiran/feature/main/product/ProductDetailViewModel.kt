package com.alireza.nikeiran.feature.main.product

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.comman.KEY_EXTRA_DATA
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.repo.CartRepoImpl
import com.alireza.nikeiran.data.repo.CartRepository
import com.alireza.nikeiran.data.repo.CommentRepoImpl
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.data.source.comment.Comment
import io.reactivex.Completable

class ProductDetailViewModel(bundle: Bundle,commentRepoImpl: CommentRepoImpl , val cartRepository: CartRepository) :NikeViewModel() {
    val productLiveData=MutableLiveData<Product>()

    private val _commentList=MutableLiveData<List<Comment>>()
    val commentListLiveData:LiveData<List<Comment>>
    get() = _commentList

    init {
        productLiveData.value=bundle.getParcelable(KEY_EXTRA_DATA)
        progressIndicatorLiveData.value=true

        commentRepoImpl.getAll(productLiveData.value!!.id)
            .async()
            .doFinally { progressIndicatorLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    _commentList.value=t
                }

            })

    }


    fun addToCart():Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()
}