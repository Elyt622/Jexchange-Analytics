package com.explwa.jexchange.app.module.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.explwa.jexchange.app.module.home.adapter.TokenRecyclerViewAdapter
import com.explwa.jexchange.databinding.FragmentHomeBinding
import com.explwa.jexchange.presenter.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
        with(binding) {
            textviewLinkExchange.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://"+textviewLinkExchange.text.toString())
                    )
                )
            }
        }
    }

    private fun configRecyclerView() {
        with(binding) {
            recyclerViewTokens.layoutManager = LinearLayoutManager(requireContext())
            viewModel.getAllTokensOnJexchange()
                .subscribeBy {
                    recyclerViewTokens.adapter = TokenRecyclerViewAdapter(it)
                }
        }
    }

}