package com.explwa.jexchangeanalytics.app.module.marketplace.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.app.module.marketplace.utils.Utils
import com.explwa.jexchangeanalytics.app.module.token.TokenActivity
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.databinding.ViewHolderTokensBinding

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class TokenListAdapter(
    data: List<UITokenItem>
) : ListAdapter<UITokenItem, TokenListAdapter.ViewHolder>(callback) {

    lateinit var context: Context

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
        context = parent.context
        return ViewHolder(
            ViewHolderTokensBinding.inflate(
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

    inner class ViewHolder(private val binding: ViewHolderTokensBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (token: UITokenItem.Cell) {
            with(binding) {
                Glide.with(root.context).load(token.pngUrl).into(imageViewToken)
                Glide.with(root.context).load(token.pngUrl).into(imageViewLogoBackground)
                textviewTokenId.text = token.identifier
                textviewPriceToken.text = Utils.toFormatString(token.price.toString())
                textviewTokenName.text = token.name
                materialCardView.setOnClickListener {
                    val intent = Intent(context, TokenActivity::class.java)
                    intent.putExtra("IDENTIFIER", token.identifier)
                    context.startActivity(intent)
                }
            }
        }

    }

}