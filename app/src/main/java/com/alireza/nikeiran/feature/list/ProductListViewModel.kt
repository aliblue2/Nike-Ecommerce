package com.alireza.nikeiran.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.NikeCompletableObserver
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.repo.ProductRepoImpl
import com.alireza.nikeiran.data.repo.ProductRepository
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.data.source.SORT_LATEST
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(val productRepository: ProductRepository, var sort:Int) :NikeViewModel(){

    private val _productListLiveData=MutableLiveData<List<Product>>()
    val productLiveData:LiveData<List<Product>>
    get() = _productListLiveData

    val sortLiveData=MutableLiveData<Int>()
    val sortTittles= arrayOf(R.string.lastest,R.string.popular,R.string.expensive,R.string.poor)
    init {
        getProducts()
        sortLiveData.value = sortTittles[sort]
    }
    fun getProducts(){
        progressIndicatorLiveData.value=true
        productRepository.getProductList(sort).async()
            .doFinally { progressIndicatorLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productListLiveData.value=t
                }

            })
    }


    fun changeSortByUser(sort:Int){
        this.sort =sort
        this.sortLiveData.value=sortTittles[sort]
        getProducts()

    }

    fun onBtnFavorClick(product: Product){
        if (product.isFavorite)
            productRepository.deleteFromFavoriteProduct(product).subscribeOn(Schedulers.io())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavoriteProduct(product).subscribeOn(Schedulers.io())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
    }

}