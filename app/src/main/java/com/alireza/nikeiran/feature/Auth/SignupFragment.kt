package com.alireza.nikeiran.feature.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.NikeCompletableObserver
import com.alireza.nikeiran.comman.NikeFragment
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment :NikeFragment() {
    val authViewModel:AuthViewModel by viewModel()
    val compositeDisposable=CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGoLoginFr=view.findViewById<MaterialButton>(R.id.btnGoLoginfr)
        btnGoLoginFr.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerAuth,LoginFragment())
            }.commit()
        }

        val email=view.findViewById<EditText>(R.id.emailSignUpEt)
        val password=view.findViewById<EditText>(R.id.passwordSignUpEt)

        val btnSignUp=view.findViewById<MaterialButton>(R.id.signUpBtn)
        btnSignUp.setOnClickListener {
        if (email.text.isNotEmpty() && password.text.isNotEmpty()){
            authViewModel.signUp(email.text.toString(),password.text.toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        requireActivity().finish()
                    Toast.makeText(requireContext(),R.string.successSignUp,Toast.LENGTH_SHORT).show()
                    }

                })
        }
        }
    }


    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

}