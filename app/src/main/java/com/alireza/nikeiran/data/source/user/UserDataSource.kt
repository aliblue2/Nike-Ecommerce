package com.alireza.nikeiran.data.source.user

import com.alireza.nikeiran.data.MessageResponse
import com.alireza.nikeiran.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username:String , password:String):Single<TokenResponse>

    fun signup(username:String, password: String) :Single<MessageResponse>

    fun loadToken()

    fun saveToken(token:String, refreshToken:String)

    fun getUserName():String

    fun saveUserName(username: String)

    fun signOut()
}