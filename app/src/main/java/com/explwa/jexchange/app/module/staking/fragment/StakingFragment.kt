package com.explwa.jexchange.app.module.staking.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchange.app.module.staking.adapter.StakingRecyclerViewAdapter
import com.explwa.jexchange.databinding.FragmentStakingBinding
import com.explwa.jexchange.presenter.viewModels.StakingViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun configRecyclerView() {
        with(binding) {
            recyclerViewStakingRewards.layoutManager = LinearLayoutManager(context)
            viewModel.getStakingRewards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    recyclerViewStakingRewards.adapter = StakingRecyclerViewAdapter(it)
                }
        }
    }

}