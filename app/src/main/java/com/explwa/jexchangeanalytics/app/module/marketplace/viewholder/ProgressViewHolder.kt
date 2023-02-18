package com.explwa.jexchangeanalytics.app.module.marketplace.viewholder

import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.databinding.ViewHolderProgressBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem

class ProgressViewHolder(
    binding: ViewHolderProgressBinding
) : BaseViewHolder<UITokenItem>(binding) {

    override fun onBind(item: UITokenItem) {
        //nothing to do
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        //nothing to do
    }

}