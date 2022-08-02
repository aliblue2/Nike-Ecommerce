package com.alireza.nikeiran.feature.Auth

import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.data.repo.UserRepoImpl
import com.alireza.nikeiran.data.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository) :NikeViewModel() {

    fun login(email:String ,password:String):Completable{
        progressIndicatorLiveData.value =true
        return userRepository.login(email,password).doFinally {
            progressIndicatorLiveData.postValue(false)
        }
    }

    fun signUp(email: String,password: String):Completable{
        progressIndicatorLiveData.value=true
        return userRepository.signup(email,password).doFinally {
            progressIndicatorLiveData.postValue(false)
        }
    }

}