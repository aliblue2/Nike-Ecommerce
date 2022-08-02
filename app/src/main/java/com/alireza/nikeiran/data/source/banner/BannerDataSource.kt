package com.alireza.nikeiran.data.source.banner

import io.reactivex.Single

interface BannerDataSource {
    fun getBannerList(): Single<List<Banner>>

}