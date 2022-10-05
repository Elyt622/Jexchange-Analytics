package com.explwa.jexchange.app.module.mytxs.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchange.app.module.mytxs.adapter.MyTxsListAdapter
import com.explwa.jexchange.databinding.FragmentMyTxsBinding
import com.explwa.jexchange.presenter.viewModels.MyTxsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class MyTxsFragment : Fragment() {

    companion object {
        fun newInstance() = MyTxsFragment()
    }

    private val viewModel: MyTxsViewModel by viewModels()

    private lateinit var binding : FragmentMyTxsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyTxsBinding.inflate(layoutInflater)
        configRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi(MyTxsViewModel.MyTxsViewModelStateLogin())

        binding.buttonCheckHerotagAddress.setOnClickListener {
            updateUi(MyTxsViewModel.MyTxsViewModelStateLoading())
            viewModel.getMyTokenTransfers(binding.editTextAddressHerotag.text.toString(), "TOLKEN-a9eb7f")
                .subscribeBy (
                    onSuccess = { txsList ->
                        updateUi(MyTxsViewModel.MyTxsViewModelStateSuccess(txsList))
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
                    rvMyTransactions.isGone = true
                }
                is MyTxsViewModel.MyTxsViewModelStateLoading -> {
                    progressCircular.isGone = state.progressBarIsGone
                    constraintLayoutHerotagAddress.isGone = state.loginIsGone
                    rvMyTransactions.isGone = true
                }
                is MyTxsViewModel.MyTxsViewModelStateSuccess -> {
                    rvMyTransactions.adapter = MyTxsListAdapter(state.myTxs, viewModel)
                    progressCircular.isGone = state.progressBarIsGone
                    rvMyTransactions.isGone = false
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

    private fun configRecyclerView() {
        binding.rvMyTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyTransactions.adapter = MyTxsListAdapter(listOf(), viewModel)
    }

}