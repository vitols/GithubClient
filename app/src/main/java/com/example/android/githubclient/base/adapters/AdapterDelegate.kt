package com.example.android.githubclient.base.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by admin on 08.03.2018.
 */
interface AdapterDelegate<T> {

    fun isForViewType(items: List<T>, position: Int): Boolean;
    fun onCreateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, items: List<T>, position: Int)
}