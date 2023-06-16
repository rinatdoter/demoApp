package com.example.testtask.presentation.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.testtask.domain.models.ListItem
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import java.util.*


class BaseDelegateAdapter<T : List<Any>?> : AbsDelegationAdapter<T> {
    constructor() : super()
    constructor(delegatesManager: AdapterDelegatesManager<T>) : super(delegatesManager)
    constructor(vararg delegates: AdapterDelegate<T>) : super(*delegates)

    var areItemsTheSameCallBack: ( (oldItem: Any?, newItem: Any?) -> Boolean ) ? = null
    var getChangePayloadCallBack: ( (oldItem: Any?, newItem: Any?) -> Any? ) ? = null


    override fun getItemCount() = items?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newItems: List<ListItem>, withDiff: Boolean = true) {
        if (withDiff) {
            val diffResult =
                DiffUtil.calculateDiff(
                    ListViewModelDiffCallback(
                        items,
                        newItems,
                        areItemsTheSameCallBack,
                        getChangePayloadCallBack
                    )
                )
            diffResult.dispatchUpdatesTo(this)
            setList(newItems)
        } else {
            setList(newItems)
            notifyDataSetChanged()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setList(newItems: List<ListItem>) {
        if (items == null) setItems(mutableListOf<T>() as T)
        (items as? MutableList<Any>)?.apply {
            clear()
            addAll(newItems)
        }
    }
}

interface OnListItemClickListener {
    fun onItemClicked(listItem: ListItem)
}

class ListViewModelDiffCallback(
    private var oldList: List<*>? = null,
    private var newList: List<*>? = null,
    private var areItemsTheSameCallBack: ( (oldItem: Any?,newItem: Any?) -> Boolean ) ? = null,
    private var getChangePayloadCallBack: ( (oldItem: Any?, newItem: Any?) -> Any? ) ? = null
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList?.size ?: 0
    override fun getNewListSize() = newList?.size ?: 0
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areItemsTheSameCallBack?.invoke(
            oldList?.get(oldItemPosition),
            newList?.get(newItemPosition)
        )
            ?: areContentsTheSame(oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldModel = oldList?.get(oldItemPosition)
        val newModel = newList?.get(newItemPosition)
        return oldModel?.equals(newModel) ?: false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return getChangePayloadCallBack?.invoke(
            oldList?.get(oldItemPosition),
            newList?.get(newItemPosition)
        ) ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }
}