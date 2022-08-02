package com.alireza.nikeiran.feature.home

import android.util.AndroidException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.comman.NikeCompletableObserver
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.repo.BannerRepository
import com.alireza.nikeiran.data.repo.ProductRepository
import com.alireza.nikeiran.data.source.*
import com.alireza.nikeiran.data.source.banner.Banner
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val productRepository: ProductRepository, bannerRepository: BannerRepository):NikeViewModel() {
    private val _productListLatest=MutableLiveData<List<Product>>()
    val productListLatest:LiveData<List<Product>>
    get() = _productListLatest

    private val _productListPopular=MutableLiveData<List<Product>>()
    val productListPopular:LiveData<List<Product>>
        get() = _productListPopular

    private val _productListExpensive=MutableLiveData<List<Product>>()
    val productListExpensive:LiveData<List<Product>>
        get() = _productListExpensive

    private val _productListPoor=MutableLiveData<List<Product>>()
    val productListPoor:LiveData<List<Product>>
        get() = _productListPoor

    private val _bannerList=MutableLiveData<List<Banner>>()
    val bannerList:LiveData<List<Banner>>
    get() = _bannerList

    val productListError=MutableLiveData<String>()
    init {
        progressIndicatorLiveData.value=true
        productRepository.getProductList(SORT_LATEST)
            .async()
            .doFinally { progressIndicatorLiveData.postValue(false) }
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productListLatest.value=t
                }

            })
        productRepository.getProductList(SORT_POPULAR).async()
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productListPopular.value=t
                }

            })

        productRepository.getProductList(SORT_PRICE_ASC).async()
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productListPoor.value=t
                }

            })

        productRepository.getProductList(SORT_PRICE_DESC).async()
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    _productListExpensive.value=t
                }

            })
        bannerRepository.getBannerList().async()
            .subscribe(object :NikeSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    _bannerList.value=t
                }

            })
    }

    fun addProductToFavorite(product: Product){
        if (product.isFavorite)
            productRepository.deleteFromFavoriteProduct(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavoriteProduct(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
    }
}