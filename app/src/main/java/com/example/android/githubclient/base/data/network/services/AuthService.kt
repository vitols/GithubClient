package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.api.RequestContainer
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call
import retrofit2.http.POST

/**
 * Created by admin on 26.02.2018.
 */
interface AuthService {
    @POST("/authorizations")
    fun tryToLogIn(request: RequestContainer): Call<String>
}