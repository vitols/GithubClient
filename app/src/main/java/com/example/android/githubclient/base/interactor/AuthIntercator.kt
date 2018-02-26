package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.api.RequestContainer
import com.example.android.githubclient.base.data.AuthRepository
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthInteractor : AuthRequestInterface {

    val repository: AuthRequestInterface = AuthRepository()

    override fun tryToLogIn(request: RequestContainer): Call<String> {
        return repository.tryToLogIn(request)
    }

}