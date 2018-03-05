package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.data.AuthRepository
import com.example.android.githubclient.base.presentation.model.User
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthInteractor {

    val repository: AuthRepository = AuthRepository()

    fun getAccessToken(code: String) : Call<String> {
        return repository.getAccessToken(code)
    }
    fun logOut() : Call<String> {
        return repository.logOut()
    }
}