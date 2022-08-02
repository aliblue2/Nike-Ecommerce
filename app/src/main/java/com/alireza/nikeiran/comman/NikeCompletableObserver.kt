package com.alireza.nikeiran.comman

import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class NikeCompletableObserver(val compositeDisposable: CompositeDisposable) :CompletableObserver{
    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(throwable = e))
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }
}