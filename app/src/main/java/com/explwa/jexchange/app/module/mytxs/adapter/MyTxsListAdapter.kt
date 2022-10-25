package com.explwa.jexchange.app.module.mytxs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.explwa.jexchange.app.module.mytxs.utils.Utils
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.databinding.ViewHolderMyTokenTransferBinding

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class MyTxsListAdapter(
    data: List<TransactionsResponse>
) : ListAdapter<TransactionsResponse, MyTxsListAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<TransactionsResponse>() {
            override fun areItemsTheSame(oldItem: TransactionsResponse, newItem: TransactionsResponse) =
                oldItem.originalTxHash == newItem.originalTxHash

            override fun areContentsTheSame(oldItem: TransactionsResponse, newItem: TransactionsResponse) =
                oldItem == newItem
        }
    }

    var list: List<TransactionsResponse>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewHolderMyTokenTransferBinding.inflate(
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

    inner class ViewHolder(private val binding: ViewHolderMyTokenTransferBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (transactionsResponse: TransactionsResponse) {
            with(binding) {
                textviewDate.text = Utils.getDateTime(transactionsResponse.timestamp.toString())

                if(!transactionsResponse.action?.argumentsResponse?.transfers.isNullOrEmpty())
                    textviewAmountToken1.text = Utils.fromBigIntegerToBigDecimal(
                        transactionsResponse.action?.argumentsResponse?.transfers?.get(0)?.value,
                        transactionsResponse.action?.argumentsResponse?.transfers?.get(0)?.decimals
                    ).toPlainString()
                else textviewAmountToken1.text = ""

                if(!transactionsResponse.action?.argumentsResponse?.transfers.isNullOrEmpty())
                    textviewNameToken.text =
                        transactionsResponse.action?.argumentsResponse?.transfers?.get(0)?.ticker.toString()
                else textviewNameToken.text = ""

                textviewAction.text = transactionsResponse.function.toString()
            }
        }

    }
}