package com.example.android.githubclient.base.presentation.presenter

import android.support.v4.app.Fragment
import android.util.Log
import com.example.android.githubclient.base.api.RequestContainer
import com.example.android.githubclient.base.interactor.AuthInteractor
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.base.presentation.view.AuthView
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthPresenter(override var view: AuthView<*>?) : BasePresenter<AuthView<*>>{

    private val interactor = AuthInteractor()


    fun tryToLogIn(login: String, password: String, tag: String): String  {
        interactor.tryToLogIn(login, password).execute()
        return ""
        //Log.e("AuthPresenter", request.toString())
        /*if (interactor.tryToLogIn(login, password)) {
            view!!.showScreen(screen)
        } else {
            view!!.showError()
        }*/
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}