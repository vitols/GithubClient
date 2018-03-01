package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestAuth
import com.example.android.githubclient.base.data.network.services.AuthService
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthNetworkDataManager : AuthRequestInterface {

    private var serviceAuth: AuthService? = null

    constructor() {
        serviceAuth = RestAuth.createService(AuthService::class.java)
    }

    override fun getAccessToken(client_id: String, client_secret: String, code: String): Call<String> {
        return serviceAuth!!.getAccessToken(client_id, client_secret, code)
    }

}