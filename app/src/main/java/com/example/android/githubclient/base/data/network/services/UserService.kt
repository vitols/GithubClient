package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by admin on 26.02.2018.
 */
interface UserService {

    @GET(ConstValues.Path.GET_USER)
    fun getMe(): Call<User>

    @GET(ConstValues.Path.GET_USERS)
    fun getUsers(): Observable<List<User>>

    @GET("/users/{user}")
    fun getUserByLogin(@Path("user") loign: String): Call<User>

    @GET(ConstValues.Path.SEARCH_USERS)
    fun searchUsers(@Query("q") q: String): Call<SearchModel>

}