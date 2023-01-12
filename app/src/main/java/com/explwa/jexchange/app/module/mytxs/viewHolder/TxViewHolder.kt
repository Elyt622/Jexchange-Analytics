package com.explwa.jexchange.app.module.mytxs.viewHolder

import com.explwa.jexchange.app.module.mytxs.utils.Utils
import com.explwa.jexchange.app.protocols.CellSelectionDelegate
import com.explwa.jexchange.app.utils.BaseViewHolder
import com.explwa.jexchange.databinding.ViewHolderMyTokenTransferBinding
import com.explwa.jexchange.presenter.model.UITxItem

class TxViewHolder(
    private val binding: ViewHolderMyTokenTransferBinding
) : BaseViewHolder<UITxItem>(binding) {

    override fun onBind(item: UITxItem) {
        if (item is UITxItem.Cell) {
            with(binding) {
                textviewDate.text = Utils.getDateTime(item.timestamp.toString())

                if (!item.action?.argumentsResponse?.transfers.isNullOrEmpty())
                    textviewAmountToken1.text = Utils.fromBigIntegerToBigDecimal(
                        item.action?.argumentsResponse?.transfers?.get(0)?.value,
                        item.action?.argumentsResponse?.transfers?.get(0)?.decimals
                    ).toPlainString()
                else textviewAmountToken1.text = ""

                if (!item.action?.argumentsResponse?.transfers.isNullOrEmpty())
                    textviewNameToken.text =
                        item.action?.argumentsResponse?.transfers?.get(0)?.ticker.toString()
                else textviewNameToken.text = ""

                textviewAction.text = item.function.toString()
            }
        }
    }

    override fun onClick(delegate: CellSelectionDelegate, index: Int) {
        binding.root.setOnClickListener {
            delegate.didSelectCellAt(index)
        }
    }

}