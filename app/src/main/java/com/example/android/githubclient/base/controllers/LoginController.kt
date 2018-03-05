package com.example.android.githubclient.base.controllers

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.utils.Prefs
import okhttp3.Authenticator

/**
 * Created by admin on 23.02.2018.
 */
class LoginController private constructor(){

    private object Holder { val INSTANCE = LoginController() }

    companion object {
        val instance: LoginController by lazy { Holder.INSTANCE }
    }

    var user: User? = null
    get() {
        if (field == null)
            field = Prefs.load("USER", User::class.java)
        Log.e("LoginControllerUser", field.toString() + "!")
        return field
    }
    set(value) {
        field = value
        if (field != null)
            Prefs.save("USER", field)
    }

    var accessToken: String = ""
    get() {
        if (field.isNullOrEmpty())
            if(Prefs.contains(ConstValues.ParamNames.ACCESS_TOKEN))
                field = Prefs.load(ConstValues.ParamNames.ACCESS_TOKEN, field::class.java)
        return field
    }
    set(value) {
        field = value
        Prefs.save(ConstValues.ParamNames.ACCESS_TOKEN, field)
    }

    var authenticator: Authenticator = Authenticator { _, response ->
        if (response.code() != 200)
            throw NetworkErrorException(response.message())
        return@Authenticator response.request()
    }

    var tryToLogOut = false

    fun isLoggedIn(): Boolean {
        Log.e("isLoggedIn", (Prefs.contains("USER") && !accessToken.isNullOrEmpty()).toString())
        return Prefs.contains("USER") && accessToken != null
    }

    fun logOut() {
        Log.e("LoginControllerLogOut", "here")
        user = null
        Prefs.remove("USER")
        Prefs.remove(ConstValues.ParamNames.ACCESS_TOKEN)
    }

}