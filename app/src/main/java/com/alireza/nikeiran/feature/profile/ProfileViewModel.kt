package com.alireza.nikeiran.feature.profile

import com.alireza.nikeiran.comman.NikeViewModel
import com.alireza.nikeiran.data.repo.UserRepository
import com.alireza.nikeiran.data.source.user.TokenContainer

class ProfileViewModel(val userRepository: UserRepository):NikeViewModel() {

    val username :String
    get() =  userRepository.getUserName()

    val isSignIn : Boolean
    get() = TokenContainer.token != null

    fun signOut(){
        userRepository.signOut()
    }
}