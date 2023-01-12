package com.explwa.jexchange.app.module.staking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchange.app.module.staking.utils.Utils
import com.explwa.jexchange.databinding.ViewHolderStakingBinding
import com.explwa.jexchange.presenter.model.UITokenItem
import java.math.BigDecimal
import java.math.RoundingMode

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class StakingListAdapter(
    data: List<UITokenItem>
) : ListAdapter<UITokenItem, StakingListAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<UITokenItem>() {
            override fun areItemsTheSame(oldItem: UITokenItem, newItem: UITokenItem) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UITokenItem, newItem: UITokenItem) =
                oldItem == newItem
        }
    }

    var list: List<UITokenItem>
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
        holder.bind(getItem(position) as UITokenItem.Cell)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: ViewHolderStakingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (token: UITokenItem.Cell) {
            with(binding) {
                textviewNameToken.text = token.identifier?.substringBefore('-')

                textviewNumberToken.text = Utils.fromBigIntegerToBigDecimal(
                    token.balance,
                    token.decimals
                ).toPlainString()

                if(token.price != null)
                    textviewTokensDollars.text = (Utils.fromBigIntegerToBigDecimal(
                        token.balance!!,
                        token.decimals
                    ) * BigDecimal(
                        token.price!!)
                            ).setScale(2, RoundingMode.DOWN)
                        .toPlainString()
                else
                    textviewTokensDollars.text = ""

                Glide.with(root.context).load(token.pngUrl).into(imageViewToken)
            }
        }

    }
}