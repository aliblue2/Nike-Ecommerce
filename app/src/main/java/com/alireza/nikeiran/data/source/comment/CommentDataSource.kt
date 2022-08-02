package com.alireza.nikeiran.data.source.comment

import com.alireza.nikeiran.data.source.comment.Comment
import io.reactivex.Completable
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(productId:Int):Single<List<Comment>>

    fun insert():Completable
}