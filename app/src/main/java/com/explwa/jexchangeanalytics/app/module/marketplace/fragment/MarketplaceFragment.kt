package com.explwa.jexchangeanalytics.app.module.marketplace.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchangeanalytics.app.module.marketplace.adapter.TokenListAdapter
import com.explwa.jexchangeanalytics.app.utils.BaseFragment
import com.explwa.jexchangeanalytics.databinding.FragmentMarketplaceBinding
import com.explwa.jexchangeanalytics.presenter.viewModels.MarketplaceViewModel
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class MarketplaceFragment : BaseFragment() {

    private val viewModel: MarketplaceViewModel by viewModels()

    private lateinit var binding: FragmentMarketplaceBinding

    var adapter = TokenListAdapter(listOf())

    companion object {
        fun newInstance() = MarketplaceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketplaceBinding.inflate(layoutInflater)
        configRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getViewState(
            viewCreated = viewCreatedSubject,
            displayProgress = adapter.displayProgressSubject,
            refresh = binding.swipe.refreshes()
        ).subscribeBy(
            onNext = { state ->
                when (state) {
                    is MarketplaceViewModel.ShowLoading -> {
                        with(binding) {
                            progressCircular.isGone = state.progressBarGone
                        }
                    }
                    is MarketplaceViewModel.ShowTokens -> {
                        with(binding) {
                            swipe.isRefreshing = false
                            progressCircular.isGone = state.progressBarGone
                            Log.d("DEBUG", state.tokens.size.toString())
                            adapter.submitList(state.tokens)
                        }
                    }
                    is MarketplaceViewModel.ShowError -> {
                        binding.swipe.isRefreshing = false
                        Toast.makeText(
                            requireContext(),
                            state.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> { }
                }
                MarketplaceViewModel.ShowTokens(state.tokens)
            },
            onError = {
                MarketplaceViewModel.ShowError(it.message.toString())
            }
        ).addTo(disposable)
    }


        private fun configRecyclerView() {
            with(binding) {
                recyclerViewTokens.layoutManager = LinearLayoutManager(requireContext())
                recyclerViewTokens.adapter = adapter
            }
        }
}
