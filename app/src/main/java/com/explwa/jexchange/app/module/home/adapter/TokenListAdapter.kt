package com.explwa.jexchange.app.module.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchange.app.module.utils.Utils
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.databinding.ViewHolderTokensBinding

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class TokenListAdapter(
    data: List<TokenResponse>
) : ListAdapter<TokenResponse, TokenListAdapter.ViewHolder>(callback) {

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
            ViewHolderTokensBinding.inflate(
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

    inner class ViewHolder(private val binding: ViewHolderTokensBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (tokenResponse: TokenResponse) {
            with(binding) {
                Glide.with(root.context).load(tokenResponse.assets?.pngUrl).into(imageViewToken)
                Glide.with(root.context).load(tokenResponse.assets?.pngUrl).into(imageViewLogoBackground)
                textviewTokenId.text = tokenResponse.identifier
                textviewPriceToken.text = Utils.toFormatString(tokenResponse.price.toString())
                textviewTokenName.text = tokenResponse.name
            }
        }

    }

}