package com.example.android.githubclient.base.data.network.services

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by admin on 26.02.2018.
 */
interface AuthService {
    @POST("/login/oauth/access_token")
    fun getAccessToken(@Header("client_id") client_id: String,
                   @Header("client_secret") client_secret: String,
                   @Header("code") code: String)
            : Call<String>
}

