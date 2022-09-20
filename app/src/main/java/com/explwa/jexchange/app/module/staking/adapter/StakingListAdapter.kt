package com.explwa.jexchange.app.module.staking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchange.app.module.utils.Utils
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.databinding.ViewHolderStakingBinding
import java.math.BigDecimal
import java.math.RoundingMode

class StakingListAdapter(
    private val data: List<TokenResponse>
) : RecyclerView.Adapter<StakingListAdapter.ViewHolder>() {

    private lateinit var binding : ViewHolderStakingBinding

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ViewHolderStakingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            Glide.with(context).load(data[position].assets?.pngUrl).into(imageToken)
            nameToken.text = data[position].identifier?.substringBefore('-')
            numberToken.text =
                Utils.fromBigIntegerToBigDecimal(
                    Utils.divideBigIntegerBy2(
                        data[position].balance),
                    data[position].decimals
                ).toPlainString()

            if(data[position].price != null)
                tokenPrice.text =
                    (Utils.fromBigIntegerToBigDecimal(
                        Utils.divideBigIntegerBy2(
                            data[position].balance!!
                        ), data[position].decimals
                    ) * BigDecimal(
                        data[position].price!!)
                            ).setScale(2, RoundingMode.DOWN)
                        .toPlainString()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nameToken = binding.textviewNameToken
        val numberToken = binding.textviewNumberToken
        val imageToken = binding.imageViewToken
        val tokenPrice = binding.textviewTokensDollars
    }
}