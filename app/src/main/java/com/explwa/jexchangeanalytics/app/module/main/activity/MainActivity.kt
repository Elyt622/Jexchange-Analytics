package com.explwa.jexchangeanalytics.app.module.main.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.explwa.jexchangeanalytics.app.module.main.adapter.ViewpagerAdapter
import com.explwa.jexchangeanalytics.presenter.viewModels.MainViewModel
import com.explwa.jexchangeanalytics.R
import com.explwa.jexchangeanalytics.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configViewPager()
        configBottomNav()
    }

    private fun configViewPager() {
        with(binding) {
            binding.viewpager.adapter = ViewpagerAdapter(this@MainActivity)
            viewpager.isUserInputEnabled = false

            viewpager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        when (position) {
                            0 -> bottomNavigationView.menu.getItem(0).isChecked = true
                            1 -> bottomNavigationView.menu.getItem(1).isChecked = true
                            2 -> bottomNavigationView.menu.getItem(2).isChecked = true
                        }
                        super.onPageSelected(position)
                    }
                }
            )
        }
    }

    private fun configBottomNav() {
        with(binding) {
            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> viewpager.currentItem = 0
                    R.id.analytics -> viewpager.currentItem = 1
                    R.id.staking -> viewpager.currentItem = 2
                }
                true
            }
        }
    }
}
