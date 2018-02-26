package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RequestContainer
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.data.network.services.AuthService
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthNetworkDataManager : AuthRequestInterface {

    private var service: AuthService? = null

    constructor() {
        service = RestApi.createService(AuthService::class.java)
    }

    override fun tryToLogIn(request: RequestContainer): Call<String> {
        return service!!.tryToLogIn(request)
    }

}