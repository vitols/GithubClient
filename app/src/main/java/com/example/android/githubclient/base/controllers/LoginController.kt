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
            field = Prefs.load("USER")
        return field
    }
    set(value) {
        field = value
        if (field != null)
            Prefs.save("USER", field)
    }

    var accessToken: String = ""
    get() {
        if (field != null && !field.isEmpty())
            field = Prefs.load(ConstValues.Prefs.ACCESS_TOKEN)
        return field
    }
    set(value) {
        field = value
        Prefs.save(ConstValues.Prefs.ACCESS_TOKEN, field)
    }

    var authenticator: Authenticator = Authenticator { _, response ->
        if (response.code() != 200)
            throw NetworkErrorException(response.message())
        return@Authenticator response.request()
    }

    fun isLoggedIn(): Boolean {
        return Prefs.contains("USER") && !accessToken.isNullOrEmpty()
    }

}