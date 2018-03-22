package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 22.02.2018.
 */
interface UsersRequestInterface {
    fun getMe(): Call<User>?
    fun getUsers(): Observable<List<User>>?
    fun getUserByLogin(login: String): Call<User>?
    fun searchUsers(q: String): Call<SearchModel<User>>?
    fun getFollowersByLogin(login: String): Observable<List<User>>?
    fun getFollowingByLogin(login: String): Observable<List<User>>?
    fun updateUser(name: String, bio: String, company: String, location: String,
                   email: String, blog: String, hireable: Boolean): Call<User>?
}