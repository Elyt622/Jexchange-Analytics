package com.explwa.jexchange.app.module.staking.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchange.data.response.staking.StakingResponse
import com.explwa.jexchange.databinding.ViewHolderStakingBinding
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class StakingRecyclerViewAdapter(
    private val data: List<StakingResponse>
) : RecyclerView.Adapter<StakingRecyclerViewAdapter.ViewHolder>() {

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
            numberToken.text = convertBigIntegerToBigDecimal(
                divideBigIntegerBy2(
                    data[position].balance!!
                ), data[position].decimals!!).toString()

            if(data[position].price != null)
                tokenPrice.text =
                    (convertBigIntegerToBigDecimal(
                        divideBigIntegerBy2(data[position].balance!!),
                        data[position].decimals!!
                    ) * BigDecimal(data[position].price!!)
                        .setScale(2, RoundingMode.DOWN))
                        .toString()
        }
    }

    private fun divideBigIntegerBy2(string: String) : BigInteger{
        return BigInteger(string).divide(BigInteger("2"))
    }

    private fun convertBigIntegerToBigDecimal(bigInteger: BigInteger, decimals: Int) : BigDecimal {
        return BigDecimal(bigInteger,decimals).setScale(2, RoundingMode.DOWN)
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