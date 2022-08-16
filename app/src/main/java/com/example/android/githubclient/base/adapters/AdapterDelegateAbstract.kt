package com.example.android.githubclient.base.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by admin on 08.03.2018.
 */
abstract class AdapterDelegateAbstract<I: T, T, ViewHolderT: RecyclerView.ViewHolder> : AdapterDelegate<T> {
    override fun isForViewType(items: List<T>, position: Int): Boolean {
        return isForViewType(items[position])
    }
    override abstract fun onCreateViewHolder(parent: ViewGroup) : ViewHolderT
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, items: List<T>, position: Int) {
        onBindViewHolder(holder as ViewHolderT, items[position] as I)
    }


    abstract fun isForViewType(item: T) : Boolean
    abstract fun onBindViewHolder(holder: ViewHolderT, item: I)
}