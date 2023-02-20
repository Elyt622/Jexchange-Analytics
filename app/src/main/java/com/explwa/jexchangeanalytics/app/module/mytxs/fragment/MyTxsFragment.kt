package com.explwa.jexchangeanalytics.app.module.mytxs.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchangeanalytics.app.module.mytxs.adapter.MyTxsListAdapter
import com.explwa.jexchangeanalytics.app.utils.BaseFragment
import com.explwa.jexchangeanalytics.databinding.FragmentMyTxsBinding
import com.explwa.jexchangeanalytics.presenter.viewModels.MyTxsViewModel
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class MyTxsFragment : BaseFragment() {

    companion object {
        fun newInstance() = MyTxsFragment()
    }

    private val viewModel: MyTxsViewModel by viewModels()

    private lateinit var binding : FragmentMyTxsBinding

    var adapter = MyTxsListAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyTxsBinding.inflate(layoutInflater)
        configRecyclerView()
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi(MyTxsViewModel.ShowLoading())

        viewModel.account.invoke()
            .subscribeBy { currentAccount ->
                viewModel.getViewState(
                    currentAccount.address.toString(),
                    "JEX-9040ca",
                    viewCreatedSubject,
                    adapter.displayProgressSubject,
                    binding.swipe.refreshes()
                ).subscribeBy(
                    onNext = { txsList ->
                        updateUi(MyTxsViewModel.ShowTxs(txsList.myTxs))
                        binding.swipe.isRefreshing = false
                    },
                    onError = {
                        updateUi(
                            MyTxsViewModel.ShowError(
                                "The address or herotag is invalid"
                            )
                        )
                    }
                )
            }
    }


    private fun updateUi(state : MyTxsViewModel.ViewState) {
        with(binding) {
            when (state) {
                is MyTxsViewModel.ShowLoading -> {
                    progressCircular.isGone = state.progressBarIsGone
                    materialCardViewAutocomplete.isGone = true
                    rvMyTransactions.isGone = true
                }
                is MyTxsViewModel.ShowTxs -> {
                    adapter.submitList(state.myTxs)
                    progressCircular.isGone = state.progressBarIsGone
                    rvMyTransactions.isGone = false
                    materialCardViewAutocomplete.isGone = false
                }
                is MyTxsViewModel.ShowError -> {
                    Toast.makeText(
                        requireActivity(),
                        state.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun configRecyclerView() {
        with(binding) {
            rvMyTransactions.layoutManager = LinearLayoutManager(requireContext())
            rvMyTransactions.adapter = adapter
        }
    }
}