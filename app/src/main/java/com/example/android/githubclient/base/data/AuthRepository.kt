package com.example.android.githubclient.base.data

import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.data.network.AuthNetworkDataManager
import com.example.android.githubclient.base.requests.AuthRequestInterface
import retrofit2.Call

/**
 * Created by admin on 26.02.2018.
 */
class AuthRepository {

    private val networkDM: AuthRequestInterface = AuthNetworkDataManager()

    fun getAccessToken(code: String): Call<String> {
        return networkDM.getAccessToken(ConstValues.Auth.CLIENT_ID, ConstValues.Auth.CLIENT_SECRET, code)
    }

}