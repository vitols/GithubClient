package com.example.android.githubclient.base.presentation.view

import com.example.android.githubclient.base.presentation.model.User

/**
 * Created by admin on 01.03.2018.
 */
interface UserListView<P> : BaseView<P> {
    fun showUsers(): List<User>
}