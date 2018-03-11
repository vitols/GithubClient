package com.example.android.githubclient.base.presentation.presenter

import android.util.Log
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.interactor.UserInteractor
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import com.example.android.githubclient.base.presentation.view.UserView
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 21.02.2018.
 */
class UserPresenter(override var view: UserView<*>?) : BasePresenter<UserView<*>> {

    val interactor: UsersRequestInterface = UserInteractor()

    fun getMe() {
        interactor.getMe()?.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                LoginController.instance.user = response?.body()
                view?.showMe()
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                view?.showError(t.toString())
            }
        })
    }

    fun getUserByLogin(login: String) {
        interactor.getUserByLogin(login)?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                try {
                    view?.showUserByLogin(response?.body() as User)
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }

        })
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}