package com.explwa.jexchangeanalytics.app.module.portfolio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.explwa.jexchangeanalytics.app.module.main.activity.MainActivity
import com.explwa.jexchangeanalytics.app.module.portfolio.utils.Utils
import com.explwa.jexchangeanalytics.app.utils.BaseFragment
import com.explwa.jexchangeanalytics.databinding.FragmentPortfolioBinding
import com.explwa.jexchangeanalytics.presenter.viewModels.PortfolioViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.math.RoundingMode


@AndroidEntryPoint
class PortfolioFragment : BaseFragment() {

    lateinit var binding : FragmentPortfolioBinding

    val viewModel: PortfolioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTotalValue((activity as MainActivity).currentAccount)
            .subscribeBy {
                binding.textviewPortfolioValue.text = it.setScale(
                    2,
                    RoundingMode.UP
                ).toPlainString()
            }.addTo(disposable)

       viewModel.getJexBalance((activity as MainActivity).currentAccount)
           .subscribeBy {
                binding.textviewJexBalance.text =
                    Utils.fromBigIntegerToBigDecimal(it[0].balance, it[0].decimals).toPlainString()
            }.addTo(disposable)
    }

    companion object {
        fun newInstance() = PortfolioFragment()
    }
}