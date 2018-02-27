package com.example.android.githubclient.base.interactor

import android.util.Base64
import android.util.Log
import com.example.android.githubclient.base.data.AuthRepository
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call
import retrofit2.Response

/**
 * Created by admin on 26.02.2018.
 */
class AuthInteractor {

    val repository: AuthRepository = AuthRepository()

    fun getAccessToken(code: String) : Call<String> {
        //Log.e("AuthInteractor", )
        return repository.getAccessToken(code)
    }
}