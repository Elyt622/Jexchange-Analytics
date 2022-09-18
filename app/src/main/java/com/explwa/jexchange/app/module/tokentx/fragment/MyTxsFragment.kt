package com.explwa.jexchange.app.module.tokentx.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchange.app.module.tokentx.adapter.MyTokenTransfersAdapter
import com.explwa.jexchange.databinding.FragmentMyTxsBinding
import com.explwa.jexchange.presenter.viewModels.MyTxsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


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

        viewModel.getMyJexTransfers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { txsList ->
                binding.rvMyTransactions.adapter = MyTokenTransfersAdapter(txsList)
            }
    }

    private fun configRecyclerView() {
        binding.rvMyTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyTransactions.adapter = MyTokenTransfersAdapter(listOf())
    }

}