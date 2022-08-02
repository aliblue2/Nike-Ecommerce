package com.alireza.nikeiran.data.source.comment

import com.alireza.nikeiran.serviceHttp.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) :CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> {
        return apiService.getCommentList(productId)
    }

    override fun insert(): Completable {
        TODO("Not yet implemented")
    }
}