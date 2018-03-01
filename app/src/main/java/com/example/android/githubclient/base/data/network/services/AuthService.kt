package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.User
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by admin on 26.02.2018.
 */
interface AuthService {
    @POST(ConstValues.Path.GET_TOKEN_PATH)
    fun getAccessToken(@Query(ConstValues.ParamNames.CLIENT_ID) client_id: String,
                       @Query(ConstValues.ParamNames.CLIENT_SECRET) client_secret: String,
                       @Query(ConstValues.ParamNames.CODE) code: String)
            : Call<String>
}

