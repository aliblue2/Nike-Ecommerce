package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.source.comment.Comment
import io.reactivex.Completable
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId:Int):Single<List<Comment>>

    fun insert():Completable
}