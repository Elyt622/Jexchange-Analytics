package com.explwa.jexchange.app.module.mytxs.fragment

import android.os.Bundle
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
import com.explwa.jexchange.R
import com.explwa.jexchange.app.module.mytxs.adapter.MyTxsListAdapter
import com.explwa.jexchange.databinding.FragmentMyTxsBinding
import com.explwa.jexchange.presenter.viewModels.MyTxsViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreatedSubject.onNext(Unit)

        updateUi(MyTxsViewModel.MyTxsViewModelStateLogin())

        binding.buttonCheckHerotagAddress.setOnClickListener {
            updateUi(MyTxsViewModel.MyTxsViewModelStateLoading())
            viewModel.getViewState(
                "erd1fdq6nmaa62c0cz8f299ycsz0q8lyfr7q87gqpjwnweux5uu9pqcq68ejhz",
                "JEX-9040ca",
                viewCreatedSubject,
                adapter.displayProgressSubject
            )
                .subscribeBy (
                    onNext = { txsList ->
                        updateUi(MyTxsViewModel.MyTxsViewModelStateSuccess(txsList.myTxs))
                    },
                    onError = {
                        updateUi(MyTxsViewModel.MyTxsViewModelStateLogin())
                        updateUi(MyTxsViewModel.MyTxsViewModelStateError(
                            "The address or herotag is invalid")
                        )
                    }
                )
        }
    }

    private fun updateUi(state : MyTxsViewModel.ViewState) {
        with(binding) {
            when (state) {
                is MyTxsViewModel.MyTxsViewModelStateLogin -> {
                    constraintLayoutHerotagAddress.isGone = state.loginIsGone
                    progressCircular.isGone = state.progressBarIsGone
                    materialCardViewAutocomplete.isGone = true
                    rvMyTransactions.isGone = true
                }
                is MyTxsViewModel.MyTxsViewModelStateLoading -> {
                    progressCircular.isGone = state.progressBarIsGone
                    constraintLayoutHerotagAddress.isGone = state.loginIsGone
                    materialCardViewAutocomplete.isGone = true
                    rvMyTransactions.isGone = true
                }
                is MyTxsViewModel.MyTxsViewModelStateSuccess -> {
                    adapter.submitList(state.myTxs)
                    progressCircular.isGone = state.progressBarIsGone
                    rvMyTransactions.isGone = false
                    materialCardViewAutocomplete.isGone = false
                    constraintLayoutHerotagAddress.isGone = state.loginIsGone
                }
                is MyTxsViewModel.MyTxsViewModelStateError -> {
                    Toast.makeText(
                        requireActivity(),
                        state.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

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

                    updateUi(MyTxsViewModel.MyTxsViewModelStateLoading())

                    viewModel.getViewState(
                        binding.editTextAddressHerotag.text.toString(),
                        autocompleteSearch.text.toString(),
                        viewCreatedSubject,
                        adapter.displayProgressSubject
                    ).subscribeBy (
                        onNext = { txsList ->
                            updateUi(MyTxsViewModel.MyTxsViewModelStateSuccess(txsList.myTxs))
                                    },
                        onError = {
                            updateUi(MyTxsViewModel.MyTxsViewModelStateLogin())
                            updateUi(
                                MyTxsViewModel.MyTxsViewModelStateError(
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