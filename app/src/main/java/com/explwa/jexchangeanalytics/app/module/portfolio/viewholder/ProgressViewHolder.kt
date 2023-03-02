package com.explwa.jexchangeanalytics.app.module.portfolio.viewholder

import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.databinding.ViewHolderProgressHorizontalBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem

class ProgressViewHolder(
    binding: ViewHolderProgressHorizontalBinding
) : BaseViewHolder<UITokenItem>(binding) {

    override fun onBind(item: UITokenItem) {
        //nothing to do
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        //nothing to do
    }

}