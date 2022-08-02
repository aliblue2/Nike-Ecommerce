package com.alireza.nikeiran.feature.main.product.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alireza.nikeiran.comman.NikeSingleObserver
import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.comman.async
import com.alireza.nikeiran.data.repo.CommentRepoImpl
import com.alireza.nikeiran.data.repo.CommentRepository
import com.alireza.nikeiran.data.source.comment.Comment

class CommentListViewModel(val commentRepository: CommentRepository , val productID:Int):NikeViewModel() {
    private val _commentList= MutableLiveData<List<Comment>>()
    val commentListLiveData: LiveData<List<Comment>>
        get() = _commentList
    init {
        progressIndicatorLiveData.value=true
        commentRepository.getAll(productID)
            .async()
            .doFinally { progressIndicatorLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    _commentList.value=t
                }

            })
    }
}