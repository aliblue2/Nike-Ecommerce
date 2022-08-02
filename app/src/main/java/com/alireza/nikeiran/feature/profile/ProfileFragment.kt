package com.alireza.nikeiran.feature.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.NikeFragment
import com.alireza.nikeiran.feature.Auth.AuthActivity
import com.alireza.nikeiran.feature.favorite.FavoriteProductsActivity
import com.alireza.nikeiran.feature.main.MainActivity
import com.alireza.nikeiran.feature.order.OrderHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : NikeFragment() {
    val profileViewModel : ProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteList=view.findViewById<TextView>(R.id.favoriteListTv)
        val orderHistory=view.findViewById<TextView>(R.id.orderHistoryTv)
        favoriteList.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteProductsActivity::class.java))
        }
        orderHistory.setOnClickListener {
            startActivity(Intent(requireContext(),OrderHistoryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuth()
    }

    private fun checkAuth() {
        val signInBtn=view!!.findViewById<TextView>(R.id.signInTv)
        val usernameTV=view!!.findViewById<TextView>(R.id.usernameTv)
        if (profileViewModel.isSignIn) {
            signInBtn.text = getString(R.string.signOut)
            signInBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_keyboard_arrow_left_24,0,R.drawable.signout,0)
            usernameTV.text = profileViewModel.username
            signInBtn.setOnClickListener {
                profileViewModel.signOut()
                signInBtn.text = getString(R.string.signIn)
                usernameTV.text = getString(R.string.guest_user)
                signInBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_keyboard_arrow_left_24,0,R.drawable.ic_key,0)
            }
        }else{
            signInBtn.text = getString(R.string.signIn)
            signInBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_keyboard_arrow_left_24,0,R.drawable.ic_key,0)
            if (profileViewModel.username.isEmpty()){
                usernameTV.text = getString(R.string.guest_user)
            }

            signInBtn.setOnClickListener {
                startActivity(Intent(requireContext(),AuthActivity::class.java))
            }
        }

    }

}