package com.explwa.jexchangeanalytics.app.module.marketplace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.explwa.jexchangeanalytics.app.module.marketplace.viewholder.ProgressViewHolder
import com.explwa.jexchangeanalytics.app.module.marketplace.viewholder.TokenViewHolder
import com.explwa.jexchangeanalytics.app.protocols.CellSelectionDelegate
import com.explwa.jexchangeanalytics.app.utils.BaseViewHolder
import com.explwa.jexchangeanalytics.databinding.ViewHolderProgressBinding
import com.explwa.jexchangeanalytics.databinding.ViewHolderTokensBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

// Recycler View Adapter into List Adapter
// https://medium.com/codex/stop-using-recyclerview-adapter-27c77666512b

class TokenListAdapter @Inject constructor(
    data: List<UITokenItem>
) : ListAdapter<UITokenItem, BaseViewHolder<UITokenItem>>(callback), CellSelectionDelegate {

    val tokenClickSubject: PublishSubject<Int> = PublishSubject.create()
    val displayProgressSubject: PublishSubject<Unit> = PublishSubject.create()

    companion object {
        val callback = object : DiffUtil.ItemCallback<UITokenItem>() {
            override fun areItemsTheSame(oldItem: UITokenItem, newItem: UITokenItem) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UITokenItem, newItem: UITokenItem) =
                oldItem == newItem
        }
        const val TOKEN_VIEW_TYPE = 0
        const val LOAD_MORE_VIEW_TYPE = 1
    }

    var list: List<UITokenItem>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : BaseViewHolder<UITokenItem> {
        return when (viewType) {
            TOKEN_VIEW_TYPE -> {
                val itemBinding = ViewHolderTokensBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TokenViewHolder(itemBinding)
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

    override fun onBindViewHolder(holder: BaseViewHolder<UITokenItem>, position: Int) {
        when (holder) {
            is TokenViewHolder -> {
                val model = getItem(position)
                holder.onBind(model)
            }
            is ProgressViewHolder -> {
                displayProgressSubject.onNext(Unit)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when(getItem(position)) {
            is UITokenItem.Cell -> TOKEN_VIEW_TYPE
            is UITokenItem.Progress -> LOAD_MORE_VIEW_TYPE
        }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun didSelectCellAt(index: Int) {
        val item = getItem(index)
        tokenClickSubject.onNext(index)
    }

}