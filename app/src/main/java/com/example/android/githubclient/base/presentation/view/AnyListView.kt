package com.example.android.githubclient.base.presentation.view

import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User

/**
 * Created by admin on 10.03.2018.
 */
interface AnyListView<P> : BaseView<P> {
    fun showListRepos(repos: List<Repo>)
    fun showListStarred(starred: List<Repo>)
    fun showListFollowers(followers: List<User>)
    fun showListFollowing(following: List<User>)
}