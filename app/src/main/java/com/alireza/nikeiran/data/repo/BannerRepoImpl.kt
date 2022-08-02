package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.banner.Banner
import com.alireza.nikeiran.data.source.banner.BannerDataSource
import com.alireza.nikeiran.data.source.banner.BannerDataSourceRemoteImpl
import io.reactivex.Single

class BannerRepoImpl(val bannerDataSourceRemoteImpl:BannerDataSource) :BannerRepository {
    override fun getBannerList(): Single<List<Banner>> {
        return bannerDataSourceRemoteImpl.getBannerList()
    }
}