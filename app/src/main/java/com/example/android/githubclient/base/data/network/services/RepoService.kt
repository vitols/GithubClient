package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by admin on 09.03.2018.
 */
interface RepoService {
    @GET(ConstValues.Path.GET_REPOS)
    fun getMyRepos() : Observable<List<Repo>>

    @GET("users/{user}/repos")
    fun getReposByUser(@Path("user") login: String) : Observable<List<Repo>>

    @GET("users/{user}/starred")
    fun getStarredByUser(@Path("user") login: String) : Observable<List<Repo>>

    @GET(ConstValues.Path.SEARCH_REPOS)
    fun searchRepos(@Query("q") q: String) : Call<SearchModel<Repo>>

    @GET(ConstValues.Path.GET_REPOS)
    fun getReposSorted(@Query("sort") sortParameter: String) : Observable<List<Repo>>

    @GET("users/{user}/repos")
    fun getUserReposSorted(@Path("user") login: String, @Query("sort") sortParameter: String) : Observable<List<Repo>>
}