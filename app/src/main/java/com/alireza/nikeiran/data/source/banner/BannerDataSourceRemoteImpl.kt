package com.alireza.nikeiran.data.source.banner

import com.alireza.nikeiran.serviceHttp.ApiService
import io.reactivex.Single

class BannerDataSourceRemoteImpl(val apiService: ApiService) :BannerDataSource{
    override fun getBannerList(): Single<List<Banner>> {
        return apiService.getBannerList()
    }
}