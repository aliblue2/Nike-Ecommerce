package com.alireza.nikeiran.serviceHttp

import com.alireza.nikeiran.data.MessageResponse
import com.alireza.nikeiran.data.TokenResponse
import com.alireza.nikeiran.data.source.Product
import com.alireza.nikeiran.data.source.banner.Banner
import com.alireza.nikeiran.data.source.cart.AddToCartResponse
import com.alireza.nikeiran.data.source.cart.CartItemCount
import com.alireza.nikeiran.data.source.cart.CartResponse
import com.alireza.nikeiran.data.source.comment.Comment
import com.alireza.nikeiran.data.source.order.PaymentResult
import com.alireza.nikeiran.data.source.order.SubmitOrderResponse
import com.alireza.nikeiran.data.source.orderHistory.OrderHistoryItem
import com.alireza.nikeiran.data.source.user.TokenContainer
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("product/list")
    fun getProductList(@Query("sort")sort:String):Single<List<Product>>

    @GET("banner/slider")
    fun getBannerList():Single<List<Banner>>

    @GET("comment/list")
    fun getCommentList(@Query("product_id")productId:Int):Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject) : Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeFromCart(@Body jsonObject: JsonObject) : Single<MessageResponse>

    @GET("cart/list")
    fun getCartList():Single<CartResponse>

    @POST("cart/changeCount")
    fun changeCount(@Body jsonObject: JsonObject):Single<AddToCartResponse>

    @GET("cart/count")
    fun getCartItemCount():Single<CartItemCount>


    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject) : Single<TokenResponse>

    @POST("user/register")
    fun singUp(@Body jsonObject: JsonObject):Single<MessageResponse>


    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject):Call<TokenResponse>


    @POST("order/submit")
    fun submitOrder(@Body jsonObject: JsonObject) : Single<SubmitOrderResponse>

    @GET("order/checkout")
    fun orderResult(@Query("order_id") order_id : Int) : Single<PaymentResult>

    @GET("order/list")
    fun orderHistory():Single<List<OrderHistoryItem>>

}

fun createInstanceOfApiService():ApiService{

    val okHttp=OkHttpClient.Builder().addInterceptor {
        val oldRequest=it.request()
        val newRequest=it.request().newBuilder()
        if (TokenContainer.token!=null){
            newRequest.addHeader("Authorization","Bearer ${TokenContainer.token}")
        }
        newRequest.addHeader("Accept","application/json")
        newRequest.method(oldRequest.method,oldRequest.body)
        return@addInterceptor it.proceed(newRequest.build())
    }
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    val retrofit=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .client(okHttp)
        .build()

    return retrofit.create(ApiService::class.java)
}