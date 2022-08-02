package com.alireza.nikeiran.feature.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alireza.nikeiran.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerAuth,LoginFragment())
        }.commit()
    }
}