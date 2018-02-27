package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by admin on 26.02.2018.
 */
interface UserService {

    @GET("/users")
    fun getUsers(): Observable<List<User>>

    @GET("/users/{user}")
    fun getUserByLogin(@Path("user") loign: String): Observable<User>

}