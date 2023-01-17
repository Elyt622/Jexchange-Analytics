package com.explwa.jexchange.app.module.marketplace.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchange.app.module.marketplace.adapter.TokenListAdapter
import com.explwa.jexchange.databinding.FragmentMarketplaceBinding
import com.explwa.jexchange.presenter.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class MarketplaceFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentMarketplaceBinding

    companion object {
        fun newInstance() = MarketplaceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketplaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi(HomeViewModel.HomeViewModelStateLoading())
        loadTokens()

        configRecyclerView()

        with(binding) {
            textviewLinkExchange.setOnClickListener {
                launchJexchangeOnBrowser()
            }
        }
    }

    private fun updateUi(state : HomeViewModel.ViewState) {
        with(binding) {
            when (state) {
                is HomeViewModel.HomeViewModelStateLoading -> {
                    progressCircular.isGone = state.progressBarVisibility
                }
                is HomeViewModel.HomeViewModelStateSuccess -> {
                    progressCircular.isGone = state.progressBarVisibility
                    recyclerViewTokens.adapter = TokenListAdapter(state.tokens)
                }
            }
        }
    }

    private fun configRecyclerView() {
        with(binding) {
            recyclerViewTokens.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewTokens.adapter = TokenListAdapter(listOf())
        }
    }

    private fun loadTokens() {
        viewModel.getAllTokens()
            .subscribeBy {
                updateUi(HomeViewModel.HomeViewModelStateSuccess(it))
            }
    }

    private fun launchJexchangeOnBrowser() {
        with(binding) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://" + textviewLinkExchange.text.toString())
                )
            )
        }
    }
}