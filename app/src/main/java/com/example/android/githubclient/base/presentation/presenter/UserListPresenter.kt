package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.interactor.UserInteractor
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.view.UserListView
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import retrofit2.Call

/**
 * Created by admin on 01.03.2018.
 */
class UserListPresenter(override var view: UserListView<*>?) : BasePresenter<UserListView<*>> {
    var interactor = UserInteractor()
    fun getUsers(){
        interactor.getUsers()?.subscribe(
                { it -> view?.showUsers(it) },
                { e -> view?.showError(e.message ?: "Unknown error") })
    }

    override fun onStop() {

    }
}