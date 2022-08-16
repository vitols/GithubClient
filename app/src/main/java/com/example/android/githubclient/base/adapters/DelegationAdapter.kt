package com.example.android.githubclient.base.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by admin on 08.03.2018.
 */
class DelegationAdapter<T> : BaseAdapter<T, RecyclerView.ViewHolder>() {
    val manager = AdapterManager<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return manager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < itemCount)
            manager.onBindViewHolder(items, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return manager.getItemViewType(items, position)
    }
}