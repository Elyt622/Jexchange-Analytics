package com.explwa.jexchangeanalytics.app.module.mytxs.viewHolder

import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.presenter.model.UITxItem
import com.explwa.jexchangeanalytics.databinding.ViewHolderProgressBinding

class ProgressViewHolder(
    binding: ViewHolderProgressBinding
) : BaseViewHolder<UITxItem>(binding) {

    override fun onBind(item: UITxItem) {
        //nothing to do
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        //nothing to do
    }

}