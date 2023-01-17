package com.explwa.jexchangeanalytics.app.module.mytxs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.explwa.jexchangeanalytics.app.module.mytxs.viewHolder.ProgressViewHolder
import com.explwa.jexchangeanalytics.app.module.mytxs.viewHolder.TxViewHolder
import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.presenter.model.UITxItem
import com.explwa.jexchangeanalytics.databinding.ViewHolderMyTokenTransferBinding
import com.explwa.jexchangeanalytics.databinding.ViewHolderProgressBinding
import io.reactivex.rxjava3.subjects.PublishSubject

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class MyTxsListAdapter(
    data: List<UITxItem>
) : ListAdapter<UITxItem, BaseViewHolder<UITxItem>>(callback), CellSelectionDelegate {

    val txClickSubject: PublishSubject<Int> = PublishSubject.create()
    val displayProgressSubject: PublishSubject<Unit> = PublishSubject.create()

    companion object {
        val callback = object : DiffUtil.ItemCallback<UITxItem>() {
            override fun areItemsTheSame(oldItem: UITxItem, newItem: UITxItem) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UITxItem, newItem: UITxItem) =
                oldItem == newItem
        }

        const val TX_VIEW_TYPE = 0
        const val LOAD_MORE_VIEW_TYPE = 1
    }

    var list: List<UITxItem>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UITxItem> {
        return when (viewType) {
            TX_VIEW_TYPE -> {
                val itemBinding = ViewHolderMyTokenTransferBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TxViewHolder(itemBinding)
            }
            LOAD_MORE_VIEW_TYPE -> {
                val itemBinding = ViewHolderProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProgressViewHolder(itemBinding)
            }
            else -> throw IllegalStateException("Invalid viewType")
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is UITxItem.Cell -> TX_VIEW_TYPE
            is UITxItem.Progress -> LOAD_MORE_VIEW_TYPE
        }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UITxItem>, position: Int) {
        when (holder) {
            is TxViewHolder -> {
                val model = getItem(position)
                holder.onBind(model)
            }
            is ProgressViewHolder -> {
                displayProgressSubject.onNext(Unit)
            }
        }
    }

    override fun didSelectCellAt(index: Int) {
        val item = getItem(index)
        txClickSubject.onNext(index)
    }
}