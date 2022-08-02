package com.alireza.nikeiran.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.NikeActivity
import com.alireza.nikeiran.comman.convertToPixel
import com.alireza.nikeiran.data.source.cart.CartItemCount
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {
    val mainViewModel:MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavigationMain).setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getCartItemCount()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetCartItemCount(cartItemCount: CartItemCount){
        val bottomNav=findViewById<BottomNavigationView>(R.id.bottomNavigationMain)
        val badge=bottomNav.getOrCreateBadge(R.id.cartFr)
        badge.backgroundColor = MaterialColors.getColor(bottomNav, androidx.appcompat.R.attr.colorPrimary)
        badge.badgeGravity = BadgeDrawable.BOTTOM_START
        badge.verticalOffset = convertToPixel(10f,this).toInt()
        badge.number = cartItemCount.count

        badge.isVisible = cartItemCount.count > 0
    }
}