package com.explwa.jexchangeanalytics.app.module.marketplace.viewholder

import android.content.Intent
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.app.module.marketplace.utils.Utils
import com.explwa.jexchangeanalytics.app.module.token.activity.TokenActivity
import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.databinding.ViewHolderTokensBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem

class TokenViewHolder(
    private val binding: ViewHolderTokensBinding
) : BaseViewHolder<UITokenItem>(binding) {

    override fun onBind(item: UITokenItem) {
        if (item is UITokenItem.Cell) {
            with(binding) {

                if (item.pngUrl != null) {
                    Glide.with(root.context).load(item.pngUrl).into(imageViewToken)
                    textviewTokenName.setPadding(0,0,0,0)
                    textviewTokenId.setPadding(0,0,0,0)
                    imageViewToken.isGone = false
                }
                else {
                    textviewTokenName.setPadding(24,0,0,0)
                    textviewTokenId.setPadding(24,0,0,0)
                    imageViewToken.isGone = true
                }

                Glide.with(root.context).load(item.pngUrl).into(imageViewLogoBackground)
                textviewTokenId.text = item.identifier
                textviewPriceToken.text = Utils.toFormatString(item.price.toString())
                textviewTokenName.text = item.name

                materialCardView.setOnClickListener {
                    val intent = Intent(it.context, TokenActivity::class.java)
                    intent.putExtra("IDTOKEN", item.identifier)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        binding.root.setOnClickListener {
            delegate.didSelectCellAt(index)
        }
    }

}