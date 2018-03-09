package com.example.android.githubclient.base.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup

/**
 * Created by admin on 26.02.2018.
 */
abstract class BaseAdapter<ModelT, ViewHolderT : RecyclerView.ViewHolder> : RecyclerView.Adapter<ViewHolderT>() {

    protected val items = ArrayList<ModelT>()

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int) : ModelT? {
        val item = items[position]
        return item ?: throw NullPointerException("Can't get item at position " + position)
    }

    fun addItem(item: ModelT) {
        items.add(item)
    }

    fun addItem(item: ModelT, position: Int) {
        items.add(position, item)
    }
    fun addAllItems(items: ArrayList<ModelT>) {
        Log.e("BaseAdapter", "addAllItems")
        val startPosition = this.items.size
        this.items.addAll(startPosition, items);
        if(startPosition == 0)
            notifyDataSetChanged()
        else
            notifyItemRangeChanged(startPosition, items.size)
    }
    fun replaceItem(item: ModelT, position: Int) {

    }
    fun replaceAllItems(items: ArrayList<ModelT>) {
        this.items.clear()
        addAllItems(items)
    }
    fun clearItems() {


    }
    fun removeItem(position: Int) {

    }



}