package com.example.android.githubclient.base.presentation.presenter

import android.util.Log
import com.example.android.githubclient.base.interactor.AuthInteractor
import com.example.android.githubclient.base.presentation.view.AuthView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 26.02.2018.
 */
class AuthPresenter(override var view: AuthView<*>?) : BasePresenter<AuthView<*>>{

    private val interactor = AuthInteractor()


    fun getAccessToken(code: String) {
        interactor.getAccessToken(code).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {

            };
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.e("Presenter", response!!.headers().toString())
            }
        })
        //headers["access_token"]
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}