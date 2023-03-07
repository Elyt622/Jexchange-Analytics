package com.explwa.jexchangeanalytics.app.module.portfolio.viewholder

import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.app.module.portfolio.utils.Utils
import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.databinding.ViewHolderPortfolioBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import java.math.BigDecimal
import java.math.RoundingMode

class TokenViewHolder(
    private val binding: ViewHolderPortfolioBinding
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
                textviewPriceToken.text = BigDecimal(item.price!!).setScale(2, RoundingMode.UP).toPlainString()
                if (item.balance != null)
                    textviewTokenBalance.text = Utils.fromBigIntegerToBigDecimal(item.balance, item.decimals).setScale(2, RoundingMode.UP).toPlainString()
                if (item.valueUsd != null)
                    textviewTokenValue.text = BigDecimal(item.valueUsd!!).setScale(2, RoundingMode.UP).toPlainString()
                textviewTokenName.text = item.name
            }
        }
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        binding.root.setOnClickListener {
            delegate.didSelectCellAt(index)
        }
    }

}