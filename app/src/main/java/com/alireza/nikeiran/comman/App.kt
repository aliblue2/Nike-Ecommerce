package com.alireza.nikeiran.comman

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.alireza.nikeiran.data.db.AppDatabase
import com.alireza.nikeiran.data.repo.*
import com.alireza.nikeiran.data.source.ProductRemoteDataSource
import com.alireza.nikeiran.data.source.banner.BannerDataSourceRemoteImpl
import com.alireza.nikeiran.data.source.cart.CartRemoteDataSourceImpl
import com.alireza.nikeiran.data.source.comment.CommentRemoteDataSource
import com.alireza.nikeiran.data.source.order.OrderRemoteDataSource
import com.alireza.nikeiran.data.source.user.UserLocalDataSource
import com.alireza.nikeiran.data.source.user.UserRemoteDataSource
import com.alireza.nikeiran.feature.Auth.AuthViewModel
import com.alireza.nikeiran.feature.cart.CartViewModel
import com.alireza.nikeiran.feature.checkout.CheckOutViewModel
import com.alireza.nikeiran.feature.checkout.OrderViewModel
import com.alireza.nikeiran.feature.comman.ProductListAdapter
import com.alireza.nikeiran.feature.favorite.FavoriteProductsViewModel
import com.alireza.nikeiran.feature.list.ProductListViewModel
import com.alireza.nikeiran.feature.home.HomeViewModel
import com.alireza.nikeiran.feature.main.MainViewModel
import com.alireza.nikeiran.feature.main.product.comment.CommentsListAdapter
import com.alireza.nikeiran.feature.main.product.ProductDetailViewModel
import com.alireza.nikeiran.feature.main.product.comment.CommentListViewModel
import com.alireza.nikeiran.feature.order.OrderHistoryViewModel
import com.alireza.nikeiran.feature.profile.ProfileViewModel
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoading
import com.alireza.nikeiran.serviceHttp.ImageService.ImageLoadingFresco
import com.alireza.nikeiran.serviceHttp.createInstanceOfApiService
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        val myModules= module {
            single { createInstanceOfApiService() }
            single <ImageLoading>{ ImageLoadingFresco() }
            single<SharedPreferences> { this@App.getSharedPreferences("api_setting", MODE_PRIVATE) }
            single { UserLocalDataSource(get()) }
            single { Room.databaseBuilder(this@App,AppDatabase::class.java,"db_app").build() }


            factory<ProductRepository> {ProductRepoImpl(ProductRemoteDataSource(get())
                ,get<AppDatabase>().productDao() )  }

            factory <CartRepository> { CartRepoImpl(CartRemoteDataSourceImpl(get())) }
            single <UserRepository>{ UserRepoImpl(UserRemoteDataSource(get()),
                UserLocalDataSource(get())) }

            factory<BannerRepository> { BannerRepoImpl(BannerDataSourceRemoteImpl(get())) }
            factory {(sort:Int) -> ProductListAdapter(get(),sort) }
            factory { CommentsListAdapter() }
            single <OrderRepository> { OrderRepoImpl(OrderRemoteDataSource(get())) }

            viewModel { OrderViewModel(get()) }
            viewModel { CartViewModel(CartRepoImpl(CartRemoteDataSourceImpl(get()))) }
            viewModel { HomeViewModel(get(),get()) }
            viewModel {AuthViewModel(get())}
            viewModel {(bundle:Bundle)->ProductDetailViewModel(bundle, CommentRepoImpl(CommentRemoteDataSource(get())),get()) }
            viewModel {(productId:Int) ->CommentListViewModel(CommentRepoImpl(CommentRemoteDataSource(get())),productId)}
            viewModel {(sort:Int)-> ProductListViewModel(get(),sort)}
            viewModel { MainViewModel(get()) }
            viewModel { (orderId :Int) -> CheckOutViewModel(orderId,get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }
            viewModel {OrderHistoryViewModel(get())}
        }

        startKoin{
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository:UserRepository=get()
        userRepository.loadToken()

    }
}