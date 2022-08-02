package com.alireza.nikeiran.feature.favorite

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.comman.NikeCompletableObserver
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.data.repo.ProductRepository
import com.alireza.nikeiran.data.source.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteProductsViewModel(private val productRepository: ProductRepository): NikeViewModel() {

    private val _productLiveData = MutableLiveData<List<Product>>()
    val productLive :LiveData<List<Product>>
    get() = _productLiveData

    init {
        productRepository.getFavoriteProduct().subscribeOn(Schedulers.io())
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productLiveData.postValue(t)
                }
            })
    }
    fun remove(product: Product){
        productRepository.deleteFromFavoriteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                }

            })
    }

}