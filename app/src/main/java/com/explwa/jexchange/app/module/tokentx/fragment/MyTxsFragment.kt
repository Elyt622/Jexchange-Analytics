package com.explwa.jexchange.app.module.tokentx.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchange.app.module.tokentx.adapter.MyTxsAdapter
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

        binding.buttonCheckHerotagAddress.setOnClickListener {
            viewModel.getMyTokenTransfers(binding.editTextAddressHerotag.text.toString())
                .subscribeBy (
                    onSuccess = { txsList ->
                        binding.rvMyTransactions.adapter = MyTxsAdapter(txsList)
                        binding.constraintLayoutStaking.isGone = false
                        binding.constraintLayoutHerotagAddress.isGone = true
                    },
                    onError = {
                        binding.constraintLayoutStaking.isGone = true
                        binding.constraintLayoutHerotagAddress.isGone = false
                        Toast.makeText(
                            requireActivity(),
                            "The address or herotag is invalid",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        }


    }

    private fun configRecyclerView() {
        binding.rvMyTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyTransactions.adapter = MyTxsAdapter(listOf())
    }

}