package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.api.RequestContainer
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * Created by admin on 26.02.2018.
 */
interface AuthService {
    @GET("user")
    fun tryToLogIn(@Header("client") encoded: String): Call<String>
}

