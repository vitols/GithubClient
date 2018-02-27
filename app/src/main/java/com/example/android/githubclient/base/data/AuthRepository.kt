package com.example.android.githubclient.base.data

import android.util.Log
import com.example.android.githubclient.base.api.RequestContainer
import com.example.android.githubclient.base.data.network.AuthNetworkDataManager
import com.example.android.githubclient.base.requests.AuthRequestInterface
import com.example.android.githubclient.base.utils.Prefs
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthRepository : AuthRequestInterface {

    private val networkDM: AuthRequestInterface = AuthNetworkDataManager()

    override fun tryToLogIn(encoded: String): Call<String> {
        Log.e("Repository", "login")
        return networkDM.tryToLogIn(encoded)

    }

}