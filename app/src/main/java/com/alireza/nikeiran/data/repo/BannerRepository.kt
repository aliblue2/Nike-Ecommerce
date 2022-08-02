package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.banner.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBannerList():Single<List<Banner>>
}