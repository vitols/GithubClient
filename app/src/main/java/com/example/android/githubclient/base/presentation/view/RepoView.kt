package com.example.android.githubclient.base.presentation.view

import com.example.android.githubclient.base.presentation.model.Repo

/**
 * Created by admin on 22.03.2018.
 */
interface RepoView<P> : BaseView<P> {
    fun showRepo(repo: Repo)
    fun showReadme(readme: String?)
}