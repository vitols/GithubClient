package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.model.Repo
import io.reactivex.Observable

/**
 * Created by admin on 22.02.2018.
 */
interface ReposRequestInterface {
    fun getRepos(): Observable<List<Repo>>
    fun getReposByUser(login: String): Observable<List<Repo>>
    fun getRepoByName(name: String): Observable<Repo>

}