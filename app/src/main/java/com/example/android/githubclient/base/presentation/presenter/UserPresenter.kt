package com.example.android.githubclient.base.presentation.presenter

import android.util.Log
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.interactor.RepoInteractor
import com.example.android.githubclient.base.interactor.UserInteractor
import com.example.android.githubclient.base.presentation.model.ErrorHandler
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import com.example.android.githubclient.base.presentation.view.UserView
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 21.02.2018.
 */
class UserPresenter(override var view: UserView<*>?) : BasePresenter<UserView<*>> {

    val interactor: UsersRequestInterface = UserInteractor()
    val interactorRepo = RepoInteractor()

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

    fun getMyRepos(sorted: String, login: String){
        Log.e("getMyRepos", "isCalled")
        interactorRepo.getReposSortedBy(sorted)?.subscribe(
                {it->view?.showRepos(it, login)},
                {e ->view?.showError(e.message ?: "")}
        )
    }

    fun getReposByLogin(login: String, sorted: String){
        interactorRepo.getUserReposSorted(login, sorted)?.subscribe(
                {it->view?.showRepos(it, login)},
                {e->view?.showError(e.message ?: "")}
        )
    }

    fun updateUser(name: String, bio: String, company: String, location: String,
                   email: String, blog: String, hireable: Boolean) {

        interactor.updateUser(name, bio, company, location, email, blog, hireable)?.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {

                if(response?.code() == ConstValues.ResponseCode.UNPROCESSABLE_ENTITY)
                    view?.showError(parseError(response.errorBody()))

                if(response?.code() == ConstValues.ResponseCode.OK) {
                    LoginController.instance.user = response.body()
                    view?.updateMe()
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                view?.showError(t.toString())
            }
        })
    }

    fun parseError(body: ResponseBody?): String {
        var errors = Gson().fromJson<ErrorHandler>(body?.string(), ErrorHandler::class.java).errors
        return if(errors.size != 0) errors[0].message else ""
    }


}