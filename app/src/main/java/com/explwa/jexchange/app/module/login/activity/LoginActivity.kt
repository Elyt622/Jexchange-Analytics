package com.explwa.jexchange.app.module.login.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.explwa.jexchange.app.module.main.activity.MainActivity
import com.explwa.jexchange.databinding.ActivityLoginBinding
import com.explwa.jexchange.presenter.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel : LoginViewModel by viewModels()

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonCheckHerotagAddress.setOnClickListener {
                viewModel.getAddress(
                    editTextAddressHerotag.text.toString()
                ).subscribeBy(
                    onSuccess = {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("ADDRESS", it)
                        startActivity(intent)
                    },
                    onError = {
                        Log.d("DEBUG", it.message.toString())
                    })
            }
        }
    }


}