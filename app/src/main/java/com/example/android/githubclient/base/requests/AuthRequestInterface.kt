package com.example.android.githubclient.base.requests

import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
interface AuthRequestInterface {
    fun getAccessToken(client_id: String, client_secret: String, code: String): Call<String>
    fun logOut(): Call<String>
}