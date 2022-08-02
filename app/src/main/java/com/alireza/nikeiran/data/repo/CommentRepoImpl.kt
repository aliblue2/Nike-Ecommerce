package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.comment.Comment
import com.alireza.nikeiran.data.source.comment.CommentDataSource
import com.alireza.nikeiran.data.source.comment.CommentRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class CommentRepoImpl(val commentRemoteDataSource: CommentDataSource) : CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> {
    return commentRemoteDataSource.getAll(productId)
    }

    override fun insert(): Completable {
        TODO("Not yet implemented")
    }
}