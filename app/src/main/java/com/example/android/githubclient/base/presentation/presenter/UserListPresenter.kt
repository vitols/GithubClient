package com.example.android.githubclient.base.presentation.presenter

import android.util.Log
import com.example.android.githubclient.base.interactor.UserInteractor
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.view.UserListView
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 01.03.2018.
 */
class UserListPresenter(override var view: UserListView<*>?) : BasePresenter<UserListView<*>> {
    var interactor = UserInteractor()
    fun getUsers(){
        interactor.getUsers()?.subscribe(
                { it -> view?.showUsers(it) },
                { e -> view?.showError(e.message ?: "Unknown error in UserListPresenter fun getUsers()") })
    }
    fun searchUsers(q: String) {
        interactor.searchUsers(q)?.enqueue(object: Callback<SearchModel<User>> {
            override fun onResponse(call: Call<SearchModel<User>>?, response: Response<SearchModel<User>>?) {
                try {
                    var responseModel = response?.body() as SearchModel<User>
                    view?.showUsers(responseModel.data!!)
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<SearchModel<User>>?, t: Throwable?) {
                view?.showError(t?.localizedMessage.toString())
            }

        })
    }

    override fun onStop() {

    }
}