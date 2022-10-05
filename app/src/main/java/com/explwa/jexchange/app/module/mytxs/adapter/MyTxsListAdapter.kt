package com.explwa.jexchange.app.module.mytxs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explwa.jexchange.app.module.utils.Utils
import com.explwa.jexchange.data.response.elrond.TransactionsResponse
import com.explwa.jexchange.databinding.ViewHolderMyTokenTransferBinding
import com.explwa.jexchange.presenter.viewModels.MyTxsViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy


class MyTxsListAdapter(
    private val data: List<TransactionsResponse>,
    private val viewModel: MyTxsViewModel
) : RecyclerView.Adapter<MyTxsListAdapter.ViewHolder>() {

    private lateinit var binding : ViewHolderMyTokenTransferBinding

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ViewHolderMyTokenTransferBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.dateTransfer.text = Utils.getDateTime(data[position].timestamp.toString())
        holder.amountToken.text = Utils.fromBigIntegerToBigDecimal(
            data[position].action?.argumentsResponse?.transfers?.get(0)?.value,
            data[position].action?.argumentsResponse?.transfers?.get(0)?.decimals
        ).toPlainString()

        holder.nameToken.text =
            data[position].action?.argumentsResponse?.transfers?.get(0)?.ticker.toString()

        if (data[position].originalTxHash != null) {
             viewModel.getTransactionWithHash(data[position].originalTxHash!!)
                 .subscribeBy {
                     holder.action.text = it.function.toString()
                 }
        } else {
            holder.action.text = data[position].function.toString()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameToken = binding.textviewNameToken
        val amountToken = binding.textviewAmountToken1
        val dateTransfer = binding.textviewDate
        val action = binding.textviewAction

    }
}