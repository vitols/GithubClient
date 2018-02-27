package com.example.android.githubclient.base.interactor

import android.util.Base64
import android.util.Log
import com.example.android.githubclient.base.data.AuthRepository
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthInteractor {

    val repository: AuthRequestInterface = AuthRepository()

    fun tryToLogIn(login: String, password: String): Call<String> {
        var encoded = Base64.encodeToString((login + ":" + password).toByteArray()
                , Base64.URL_SAFE)
        Log.e("Interactor", encoded)
        return repository.tryToLogIn(encoded)
    }

}