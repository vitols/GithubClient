package com.example.android.githubclient.mainScreen.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R
import com.example.android.githubclient.base.adapters.AdapterDelegateAbstract
import com.example.android.githubclient.base.presentation.model.Repo
import kotlinx.android.synthetic.main.item_repo.view.*

/**
 * Created by admin on 15.03.2018.
 */
class RepoDelegate(val context: Activity, val callback: (View, String, String) -> Unit) :
        AdapterDelegateAbstract<Any, Any, RepoDelegate.ViewHolder>() {

    override fun isForViewType(item: Any): Boolean {
        return item is Repo
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Any) {
        val repoItem = item as Repo

        holder.repoName.text = repoItem.fullName
        if(repoItem.description != null)
            holder.repoDescription.text = repoItem.description.toString()
        else
            holder.repoDescription.visibility = View.GONE

        holder.lang.text = if (repoItem.language != null) repoItem.language else "No lang"

        holder.forked.text = repoItem.forksCount.toString()
        holder.starred.text = repoItem.stargazersCount.toString()

        holder.item.setOnClickListener {
            callback(holder.item, repoItem.owner.login, repoItem.fullName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName = itemView.screen_profile_card_reponame
        val repoDescription = itemView.screen_profile_card_repo_description
        val lang = itemView.screen_profile_card_language
        val forked = itemView.screen_profile_card_forked
        val starred = itemView.screen_profile_card_starred
        val item = itemView
    }

}