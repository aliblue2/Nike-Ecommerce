package com.alireza.nikeiran.serviceHttp


import android.util.Log
import com.alireza.nikeiran.data.TokenResponse
import com.alireza.nikeiran.data.source.user.CLIENT_ID
import com.alireza.nikeiran.data.source.user.CLIENT_SECRET
import com.alireza.nikeiran.data.source.user.TokenContainer
import com.alireza.nikeiran.data.source.user.UserLocalDataSource
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import org.koin.java.KoinJavaComponent.inject

class NikeAuthenticator :Authenticator, KoinComponent {
    val apiService:ApiService by inject()
    val userLocalDataSource:UserLocalDataSource by inject()
    override fun authenticate(route: Route?, response: Response): Request? {
        if (TokenContainer.token != null && TokenContainer.refreshToken != null && !response.request.url.pathSegments.last().equals("token",false)){
            try {
                val token = refreshToken()
                if (token.isEmpty())
                    return null
                return response.request.newBuilder().addHeader("Authorization","Bearer $token").build()
            }catch (e:Exception){
                Log.e("TAG", "authenticate: ${e.message}", )

            }
        }
        return null
    }

    fun refreshToken():String{
        val response:retrofit2.Response<TokenResponse> = apiService.refreshToken(JsonObject().apply {
            addProperty("grant-type","refresh_token")
            addProperty("refresh_token",TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        }).execute()
        response.body()?.let {
            TokenContainer.updateToken(it.access_token,it.refresh_token)
            userLocalDataSource.saveToken(it.access_token,it.refresh_token)
            return it.access_token
        }

        return ""
    }

}