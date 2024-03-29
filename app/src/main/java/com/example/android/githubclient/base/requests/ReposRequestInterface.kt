package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 22.02.2018.
 */
interface ReposRequestInterface {
    fun getRepos(): Observable<List<Repo>>?
    fun getReposByUser(login: String): Observable<List<Repo>>?
    fun getRepo(login: String, name: String): Call<Repo>?
    fun getStarredByUser(login: String): Observable<List<Repo>>?
    fun searchRepos(q: String): Call<SearchModel<Repo>>?
    fun getReposSortedBy(sort: String): Observable<List<Repo>>?
    fun getUserReposSorted(login: String,sortParameter: String) : Observable<List<Repo>>?
}