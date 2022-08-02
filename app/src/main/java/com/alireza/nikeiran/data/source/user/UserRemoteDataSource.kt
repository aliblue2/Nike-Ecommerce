package com.alireza.nikeiran.data.source.user

import com.alireza.nikeiran.data.MessageResponse
import com.alireza.nikeiran.data.TokenResponse
import com.alireza.nikeiran.serviceHttp.ApiService
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single



const val CLIENT_ID = 2
const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"
class UserRemoteDataSource(val apiService: ApiService) :UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {

        return apiService.login(JsonObject().apply {
            addProperty("username",username)
            addProperty("grant_type","password")
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
            addProperty("password",password)
        })
    }

    override fun signup(username: String, password: String): Single<MessageResponse> {
        return apiService.singUp(JsonObject().apply {
            addProperty("email",username)
            addProperty("password",password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun saveUserName(username: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}