package com.example.android.githubclient.base.presentation.view

import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User

/**
 * Created by admin on 10.03.2018.
 */
interface AnyListView<P> : BaseView<P> {
    fun showListRepos(repos: List<Repo>)
    fun showListStarred(repos: List<Repo>)
    fun showListFollowers(users: List<User>)
    fun showListFollowing(users: List<User>)
}