package com.explwa.jexchangeanalytics.app.module.login.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.explwa.jexchangeanalytics.app.module.main.activity.MainActivity
import com.explwa.jexchangeanalytics.app.utils.BaseActivity
import com.explwa.jexchangeanalytics.databinding.ActivityLoginBinding
import com.explwa.jexchangeanalytics.presenter.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel : LoginViewModel by viewModels()

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonCheckHerotagAddress.setOnClickListener {
                viewModel.setAccount(
                    editTextAddressHerotag.text.toString()
                ).subscribeBy (
                        onSuccess = {
                            viewModel.insertAccountInDB(it)
                            val intent = Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                            intent.putExtra("ADDRESS", it.address)
                            startActivity(intent)
                        },
                        onError = {
                            Log.d("DEBUG", it.message.toString())
                        }
                    ).addTo(disposable)
            }
        }
    }


}