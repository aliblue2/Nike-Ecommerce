package com.alireza.nikeiran.feature.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.NikeCompletableObserver
import com.alireza.nikeiran.comman.NikeFragment
import com.alireza.nikeiran.comman.async
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment() :NikeFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment,container,false)
    }
    val authViewModel:AuthViewModel by viewModel()
    val compositeDisposable=CompositeDisposable()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email=view.findViewById<EditText>(R.id.emailLoginEt)
        val password=view.findViewById<EditText>(R.id.passwordLoginEt)

        val btnLogin=view.findViewById<MaterialButton>(R.id.loginBtn)
        btnLogin.setOnClickListener{
            authViewModel.login(email.text.toString(),password.text.toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.loginSuccess))
                        requireActivity().finish()
                    }

                })
        }
        val btnGoSignUpFra=view.findViewById<MaterialButton>(R.id.btnGoSignUpfr)
        btnGoSignUpFra.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerAuth,SignupFragment())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}