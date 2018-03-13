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
    fun getRepoByName(name: String): Observable<Repo>?
    fun getStarredByUser(login: String): Observable<List<Repo>>?
    fun searchRepos(q: String): Call<SearchModel<Repo>>?
}