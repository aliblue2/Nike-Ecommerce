package com.alireza.nikeiran.feature.main.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.connyduck.sparkbutton.SparkButton
import at.connyduck.sparkbutton.SparkEventListener
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.*
import com.alireza.nikeiran.data.source.cart.AddToCartResponse
import com.alireza.nikeiran.data.source.comment.Comment
import com.alireza.nikeiran.feature.main.product.comment.CommentListActivity
import com.alireza.nikeiran.feature.main.product.comment.CommentsListAdapter
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.view.NikeImageView
import com.alireza.nikeiran.view.scrool.ObservableScrollView
import com.alireza.nikeiran.view.scrool.ObservableScrollViewCallbacks
import com.alireza.nikeiran.view.scrool.ScrollState
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailActivity() : NikeActivity() {
    val productDetailViewModel:ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoading:ImageLoading by inject ()
    val compositeDisposable=CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        val imageProductIv=findViewById<NikeImageView>(R.id.imageProductDetailIv)
        val titleProductTv=findViewById<TextView>(R.id.titleProductDetailTv)
        val titleProductToolbarTv=findViewById<TextView>(R.id.toolbarTitleTv)
        val previousProductTv=findViewById<TextView>(R.id.previousPriceDetailTv)
        val priceProductIv=findViewById<TextView>(R.id.priceDetailTv)
        val rvComments=findViewById<RecyclerView>(R.id.rvComments)
        val buttonAddCart=findViewById<Button>(R.id.addToCartBtn)
        val btnFavorite=findViewById<SparkButton>(R.id.favoriteDetailBtn)
        val buttonShowAllComments=findViewById<Button>(R.id.btnShowAllComments)
        val adapter: CommentsListAdapter by inject()
        val icBack=findViewById<View>(R.id.backDetailProductIc)
        icBack.setOnClickListener {
            finish()
        }



        productDetailViewModel.commentListLiveData.observe(this){
            rvComments.adapter=adapter
            adapter.comments= it as ArrayList<Comment>
            if (it.size>3){
                buttonShowAllComments.visibility = View.VISIBLE
                buttonShowAllComments.setOnClickListener {
                    startActivity(Intent(this,CommentListActivity::class.java ).apply {
                        putExtra(KEY_EXTRA_ID,productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            }else
                buttonShowAllComments.visibility=View.GONE

            rvComments.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }


        productDetailViewModel.progressIndicatorLiveData.observe(this){
            setProgressIndicator(it)
        }
        productDetailViewModel.productLiveData.observe(this){
            imageLoading.load(imageProductIv,it.image)
            titleProductTv.text=it.title
            titleProductToolbarTv.text=it.title
            previousProductTv.text= formatPrice(it.previous_price)
            previousProductTv.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            priceProductIv.text= formatPrice(it.price)
        }
        imageProductIv.post {
            val productImageHeight=imageProductIv.measuredHeight
            val productImageParalax=imageProductIv
            val toolbar=findViewById<MaterialCardView>(R.id.toolbarTransparent)
            val observableScrollView=findViewById<ObservableScrollView>(R.id.observableScrollView)
            observableScrollView.addScrollViewCallbacks(object :ObservableScrollViewCallbacks{
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean,
                ) {
                   toolbar.alpha =scrollY.toFloat() / productImageHeight.toFloat()
                    productImageParalax.translationY =scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }

        buttonAddCart.setOnClickListener {
            productDetailViewModel.addToCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.successToAddCart))
                    }

                })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}