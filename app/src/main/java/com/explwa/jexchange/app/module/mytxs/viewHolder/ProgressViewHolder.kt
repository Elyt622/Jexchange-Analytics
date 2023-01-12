package com.explwa.jexchange.app.module.mytxs.viewHolder

import com.explwa.jexchange.app.protocols.CellSelectionDelegate
import com.explwa.jexchange.app.utils.BaseViewHolder
import com.explwa.jexchange.databinding.ViewHolderProgressBinding
import com.explwa.jexchange.presenter.model.UITxItem

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