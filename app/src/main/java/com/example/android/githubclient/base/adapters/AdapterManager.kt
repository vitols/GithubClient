package com.example.android.githubclient.base.adapters

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by admin on 08.03.2018.
 */
class AdapterManager<T> {
    private val delegates = SparseArrayCompat<AdapterDelegate<T>>()

    fun addDelegate(delegate: AdapterDelegate<T>): AdapterManager<T>{
        return addDelegate(delegates.size(), delegate)
    }

    fun addDelegate(viewType: Int, delegate: AdapterDelegate<T>): AdapterManager<T>{
        return addDelegate(viewType, delegate, false)
    }
    fun addDelegate(viewType: Int, delegate: AdapterDelegate<T>, allowReplacing : Boolean): AdapterManager<T>{
        if(!allowReplacing && delegates.get(viewType) != null)
            throw IllegalArgumentException("Delegate already exists for the viewType " + viewType)
        delegates.put(viewType, delegate)
        return this
    }

    fun removeDelegate(viewType: Int) {
        delegates.remove(viewType)
    }
    fun removeDelegate(delegate: AdapterDelegate<T>) {
        val index = delegates.indexOfValue(delegate)
        if(index >= 0)
            delegates.removeAt(index)
    }

    fun getItemViewType(items: List<T>, position: Int) : Int {
        for(i in 0..delegates.size()) {
            if(delegates.valueAt(i).isForViewType(items, position))
                return delegates.keyAt(i)
        }
        throw NullPointerException("Delegate at " + position + " position doesn't exist")
    }
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val adapterDelegate = delegates.get(viewType)
        if(adapterDelegate == null)
            throw NullPointerException("For ViewType " + viewType + "Delegate doesn't exist")
        val viewHolder = adapterDelegate.onCreateViewHolder(parent)
        if (viewHolder == null)
            throw NullPointerException("Can't create ViewHolder for Delegate " + adapterDelegate)
        return viewHolder
    }

    fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder) {
        val adapterDelegate = delegates.get(holder.itemViewType)
        if (adapterDelegate == null)
            throw NullPointerException("Can't bind holder to ViewType " + holder.itemViewType)
        adapterDelegate.onBindViewHolder(holder, items, position)
    }
}