package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.presentation.view.UserListView

/**
 * Created by admin on 01.03.2018.
 */
class UserListPresenter(override var view: UserListView<*>?) : BasePresenter<UserListView<*>> {
    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}