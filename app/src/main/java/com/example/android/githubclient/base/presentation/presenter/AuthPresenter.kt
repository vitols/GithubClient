package com.example.android.githubclient.base.presentation.presenter

import android.util.Log
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.interactor.AuthInteractor
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.view.AuthView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 26.02.2018.
 */
class AuthPresenter(override var view: AuthView<*>?) : BasePresenter<AuthView<*>>{

    private val interactor = AuthInteractor()

    fun logOut() {
        interactor.logOut().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.e("onResponseLogOut", response?.body())
            }

        })
    }

    fun getAccessToken(code: String) {
        interactor.getAccessToken(code).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.e("Presenter", "onFailure")
                t?.printStackTrace()
            };
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                var token: String? = parseBody(response?.body()!!)?.get(ConstValues.ParamNames.ACCESS_TOKEN)
                if(token != null) {
                    LoginController.instance.accessToken = token
                    view?.showScreen()
                }
            }
        })
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun parseBody(body: String) : HashMap<String, String>? {

        if(body.isNullOrEmpty()) {
            return null
        }

        var result: HashMap<String, String> = HashMap()
        var params = body.split("&")

        for(p in params) {
            var keyValue = p.split("=")
            result.put(keyValue[0], keyValue[1])
        }
        return result
    }
}