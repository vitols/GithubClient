package com.example.android.githubclient.mainScreen.adapterDelegates

import android.app.Activity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.githubclient.R
import com.example.android.githubclient.base.adapters.AdapterDelegateAbstract
import com.example.android.githubclient.base.presentation.model.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_screen_profile.view.*
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by admin on 08.03.2018.
 */
class UserDelegate(val context: Activity, val callback: (User) -> Unit) :
        AdapterDelegateAbstract<Any, Any, UserDelegate.ViewHolder>() {

    override fun isForViewType(item: Any): Boolean {
        return item is User
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Any) {
        var item1 = item as User
        Glide.with(context)
                .load(item1.avatarUrl)
                .centerCrop()
                .into(holder.avatar)
        holder.login.text = item1.login
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.item_user_avatar
        val login = itemView.item_user_login
    }
}