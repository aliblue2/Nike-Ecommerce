package com.alireza.nikeiran.data.repo

import com.alireza.nikeiran.data.TokenResponse
import com.alireza.nikeiran.data.source.user.TokenContainer
import com.alireza.nikeiran.data.source.user.UserLocalDataSource
import com.alireza.nikeiran.data.source.user.UserRemoteDataSource
import io.reactivex.Completable

class UserRepoImpl(val userRemoteDataSource: UserRemoteDataSource , val userLocalDataSource: UserLocalDataSource) :UserRepository {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username,password).doOnSuccess {
            onSuccessLogin(username,it)
        }
            .ignoreElement()
    }

    override fun signup(username: String, password: String): Completable {
        return userRemoteDataSource.signup(username,password).flatMap {
            userRemoteDataSource.login(username,password)
        }.doOnSuccess {
            onSuccessLogin(username,it)
        }
            .ignoreElement()
    }

    override fun loadToken() {
        return userLocalDataSource.loadToken()
    }

    override fun getUserName(): String  = userLocalDataSource.getUserName()

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.updateToken(null,null)
    }

    fun onSuccessLogin(username: String,tokenResponse: TokenResponse){
        TokenContainer.updateToken(tokenResponse.access_token,tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token,tokenResponse.refresh_token)
        userLocalDataSource.saveUserName(username)
    }
}