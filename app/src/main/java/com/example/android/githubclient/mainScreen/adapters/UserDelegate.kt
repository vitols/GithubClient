package com.example.android.githubclient.mainScreen.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.githubclient.R
import com.example.android.githubclient.base.adapters.AdapterDelegateAbstract
import com.example.android.githubclient.base.presentation.model.User
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by admin on 08.03.2018.
 */
class UserDelegate(val context: Activity, val callback: (View, String) -> Unit) :
        AdapterDelegateAbstract<Any, Any, UserDelegate.ViewHolder>() {

    override fun isForViewType(item: Any): Boolean {
        return item is User
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Any) {
        val userItem = item as User
        Glide.with(context)
                .load(userItem.avatarUrl)
                .centerCrop()
                .into(holder.avatar)
        holder.login.text = userItem.login
        holder.item.setOnClickListener {callback(holder.item, userItem.login)}
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.item_user_avatar
        val login = itemView.item_user_login
        val item = itemView
    }
}