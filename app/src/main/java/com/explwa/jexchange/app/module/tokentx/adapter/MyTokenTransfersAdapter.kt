package com.explwa.jexchange.app.module.tokentx.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explwa.jexchange.R
import com.explwa.jexchange.app.module.utils.Utils
import com.explwa.jexchange.data.response.transactions.TransactionsResponse
import com.explwa.jexchange.databinding.ViewHolderMyTokenTransferBinding


class MyTokenTransfersAdapter(
    private val data: List<TransactionsResponse>
) : RecyclerView.Adapter<MyTokenTransfersAdapter.ViewHolder>() {

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

        holder.dateTransfer.text = Utils().getDateTime(data[position].timestamp.toString())
        holder.amountToken.text = Utils().toStringFormat(
            data[position].action?.argumentsResponse?.transfers?.get(0)?.value.toString(),
            data[position].action?.argumentsResponse?.transfers?.get(0)?.decimals
        )

        holder.nameToken.text = data[position].action?.argumentsResponse?.transfers?.get(0)?.ticker.toString()

        when(data[position].function.toString()) {
            "enter_staking" -> {
                holder.amountToken.setTextColor(
                    Color.parseColor("#FFFFFF")
                )
                holder.action.text = "TO STAKING"
            }
            "fill_offer" -> {
                holder.amountToken.setTextColor(
                    Color.parseColor("#FF0000")
                )
                holder.action.text = data[position].function.toString()
            }
            "null" -> {
                holder.amountToken.setTextColor(
                    context.resources.getColor(
                        R.color.greenJex,
                        context.resources.newTheme()
                    )
                )
                holder.action.text = ""
            }
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