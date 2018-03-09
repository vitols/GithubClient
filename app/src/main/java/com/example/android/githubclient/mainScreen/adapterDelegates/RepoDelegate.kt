package com.example.android.githubclient.mainScreen.adapterDelegates

import android.app.ActionBar
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
 * Created by admin on 09.03.2018.
 */
class RepoDelegate(val context: Activity, val callback: (View) -> Unit) :
        AdapterDelegateAbstract<Any, Any, RepoDelegate.ViewHolder>() {

    override fun isForViewType(item: Any): Boolean {
        return item is Repo
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Any) {
        val item1 = item as Repo

        holder.repoName.text = item1.fullName
        if(item1.description != null)
            holder.repoDescription.text = item1.description.toString()
        else
            holder.repoDescription.visibility = View.GONE

        holder.lang.text = if (item1.language != null) item1.language else "No lang"

        holder.forked.text = item1.forksCount.toString()
        holder.starred.text = item1.stargazersCount.toString()

        holder.item.setOnClickListener(callback)
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName = itemView.screen_repos_card_reponame
        val repoDescription = itemView.screen_repos_card_repo_description
        val lang = itemView.screen_repos_card_language
        val forked = itemView.screen_repos_card_forked
        val starred = itemView.screen_repos_card_starred
        val item = itemView
    }

}