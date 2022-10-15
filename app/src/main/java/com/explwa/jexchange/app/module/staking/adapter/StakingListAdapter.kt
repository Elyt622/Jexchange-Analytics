package com.explwa.jexchange.app.module.staking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchange.app.module.staking.utils.Utils
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.databinding.ViewHolderStakingBinding
import java.math.BigDecimal
import java.math.RoundingMode

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class StakingListAdapter(
    data: List<TokenResponse>
) : ListAdapter<TokenResponse, StakingListAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<TokenResponse>() {
            override fun areItemsTheSame(oldItem: TokenResponse, newItem: TokenResponse) =
                oldItem.identifier == newItem.identifier

            override fun areContentsTheSame(oldItem: TokenResponse, newItem: TokenResponse) =
                oldItem == newItem
        }
    }

    var list: List<TokenResponse>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewHolderStakingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: ViewHolderStakingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (tokenResponse: TokenResponse) {
            with(binding) {
                textviewNameToken.text = tokenResponse.identifier?.substringBefore('-')

                textviewNumberToken.text = Utils.fromBigIntegerToBigDecimal(
                    tokenResponse.balance,
                    tokenResponse.decimals
                ).toPlainString()

                if(tokenResponse.price != null)
                    textviewTokensDollars.text = (Utils.fromBigIntegerToBigDecimal(
                        tokenResponse.balance!!,
                        tokenResponse.decimals
                    ) * BigDecimal(
                        tokenResponse.price!!)
                            ).setScale(2, RoundingMode.DOWN)
                        .toPlainString()
                else
                    textviewTokensDollars.text = ""

                Glide.with(root.context).load(tokenResponse.assets?.pngUrl).into(imageViewToken)
            }
        }

    }
}