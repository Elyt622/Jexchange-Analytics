package com.explwa.jexchangeanalytics.app.module.mytxs.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchangeanalytics.R
import com.explwa.jexchangeanalytics.app.module.mytxs.adapter.MyTxsListAdapter
import com.explwa.jexchangeanalytics.databinding.FragmentMyTxsBinding
import com.explwa.jexchangeanalytics.presenter.viewModels.MyTxsViewModel
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject


@AndroidEntryPoint
class MyTxsFragment : Fragment() {

    companion object {
        fun newInstance() = MyTxsFragment()
    }

    private var viewCreatedSubject: BehaviorSubject<Unit> = BehaviorSubject.create()

    private val viewModel: MyTxsViewModel by viewModels()

    private lateinit var binding : FragmentMyTxsBinding

    var adapter = MyTxsListAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyTxsBinding.inflate(layoutInflater)
        configRecyclerView()
        configAutocompleteTextView()
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreatedSubject.onNext(Unit)

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
                        Log.d("DEBUGGG", txsList.myTxs.size.toString())
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

    @SuppressLint("CheckResult")
    private fun configAutocompleteTextView() {
        with(binding) {
            viewModel.getAllTokensOnJexchange()
                .subscribeBy {
                    if (activity != null) {
                        val arrayAdapter = ArrayAdapter(
                            requireActivity(),
                            R.layout.simple_list,
                            it,
                        )
                        autocompleteSearch.setAdapter(arrayAdapter)
                    }
                }

            autocompleteSearch.setOnClickListener {
                autocompleteSearch.showDropDown()
            }

            autocompleteSearch.setOnFocusChangeListener { _, _ ->
                autocompleteSearch.showDropDown()
            }

            autocompleteSearch.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, _, _ ->

                    updateUi(MyTxsViewModel.ShowLoading())

                    viewModel.getViewState(
                        "",
                        autocompleteSearch.text.toString(),
                        viewCreatedSubject,
                        adapter.displayProgressSubject,
                        binding.swipe.refreshes()
                    ).subscribeBy (
                        onNext = { txsList ->
                            updateUi(MyTxsViewModel.ShowTxs(txsList.myTxs))
                            binding.swipe.isRefreshing = false
                                 },
                        onError = {
                            updateUi(
                                MyTxsViewModel.ShowError(
                                "The address or herotag is invalid")
                            )
                        }
                    )
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