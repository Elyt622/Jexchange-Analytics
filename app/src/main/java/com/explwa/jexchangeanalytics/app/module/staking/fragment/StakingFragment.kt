package com.explwa.jexchangeanalytics.app.module.staking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchangeanalytics.app.module.staking.adapter.StakingListAdapter
import com.explwa.jexchangeanalytics.presenter.viewModels.StakingViewModel
import com.explwa.jexchangeanalytics.databinding.FragmentStakingBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class StakingFragment : Fragment() {

    companion object {
        fun newInstance() = StakingFragment()
    }

    private val viewModel : StakingViewModel by viewModels()

    private lateinit var binding : FragmentStakingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStakingBinding.inflate(layoutInflater)
        configRecyclerView()
        return binding.root
    }

    private fun configRecyclerView() {
        with(binding) {
            recyclerViewStakingRewards.layoutManager = LinearLayoutManager(context)
            viewModel.getStakingRewards()
                .subscribeBy {
                    recyclerViewStakingRewards.adapter = StakingListAdapter(it)
                }
        }
    }

}