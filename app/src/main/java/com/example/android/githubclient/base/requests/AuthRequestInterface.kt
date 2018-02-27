package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.api.RequestContainer
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
interface AuthRequestInterface {
    fun tryToLogIn(encoded: String): Call<String>
}