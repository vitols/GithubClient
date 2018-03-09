package com.example.android.githubclient.base.presentation.view

import com.example.android.githubclient.base.presentation.model.Repo

/**
 * Created by admin on 09.03.2018.
 */
interface RepoListView<P> : BaseView<P> {
    fun showRepos(repos: List<Repo>)
}