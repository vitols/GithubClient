package com.example.android.githubclient.base.presentation.view

import com.example.android.githubclient.base.presentation.model.User

/**
 * Created by admin on 21.02.2018.
 */
interface UserView<P> : BaseView<P>{
    fun showMe()
    fun showUserByLogin(user: User?)
}