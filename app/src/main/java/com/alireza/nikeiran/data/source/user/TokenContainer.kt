package com.alireza.nikeiran.data.source.user

import android.util.Log

object TokenContainer {
    var token:String?=null
        private set
    var refreshToken :String?=null
        private set


    fun updateToken( token:String? , refreshToken:String?){
        Log.i("TAG", "updateToken: this is a token ${token?.substring(0,10)} and this is refreshToken ${refreshToken?.substring(0,10)}")
        this.token=token
        this.refreshToken= this.refreshToken
    }
}