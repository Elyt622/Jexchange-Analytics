package com.explwa.jexchangeanalytics.app.module.portfolio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.explwa.jexchangeanalytics.app.utils.BaseFragment
import com.explwa.jexchangeanalytics.databinding.FragmentPortfolioBinding
import com.explwa.jexchangeanalytics.presenter.viewModels.PortfolioViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PortfolioFragment : BaseFragment() {

    lateinit var binding : FragmentPortfolioBinding

    val viewModel: PortfolioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun newInstance() = PortfolioFragment()
    }
}