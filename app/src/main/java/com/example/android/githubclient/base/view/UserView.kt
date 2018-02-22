package com.example.android.githubclient.base.view

import com.example.android.githubclient.base.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface

/**
 * Created by admin on 21.02.2018.
 */
interface UserView<P> : BaseView<P>{
    fun showUsers(): List<User>
    fun showUserByLogin(): List<User>
}