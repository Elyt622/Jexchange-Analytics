package com.explwa.jexchange.app.module.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.explwa.jexchange.databinding.ActivityMainBinding
import com.explwa.jexchange.presenter.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        configToolbar()
    }

    private fun configToolbar() {
        binding.topToolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }
}