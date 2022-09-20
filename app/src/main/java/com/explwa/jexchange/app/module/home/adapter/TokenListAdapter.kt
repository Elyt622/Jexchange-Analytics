package com.explwa.jexchange.app.module.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explwa.jexchange.data.response.elrond.TokenResponse
import com.explwa.jexchange.databinding.ViewHolderTokensBinding

class TokenListAdapter(
    private val data: List<TokenResponse>
) : RecyclerView.Adapter<TokenListAdapter.ViewHolder>() {

    private lateinit var binding : ViewHolderTokensBinding

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ViewHolderTokensBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            Glide.with(context).load(data[position].assets?.pngUrl).into(imageToken)
            identifier.text = data[position].identifier
            price.text = data[position].price.toString()
            name.text = data[position].name
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val identifier = binding.textviewTokenId
        val name = binding.textviewTokenName
        val price = binding.textviewPriceToken
        val imageToken = binding.imageViewToken
    }

}