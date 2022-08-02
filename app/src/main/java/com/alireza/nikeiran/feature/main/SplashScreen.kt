package com.alireza.nikeiran.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.core.view.ContentInfoCompat
import com.alireza.nikeiran.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(object :Runnable{
            override fun run() {
                val intent=Intent(this@SplashScreen,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        },3000)
    }
}