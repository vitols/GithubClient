package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.data.network.services.RepoService
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 23.02.2018.
 */
class RepoNetworkDataManager : ReposRequestInterface{

    private var serviceAuthorized: RepoService? = null
    private var serviceNonAuthorized: RepoService? = null

    constructor() {
        serviceAuthorized = RestApi.createService(RepoService::class.java)
    }

    override fun getRepos(): Observable<List<Repo>>? {
        return serviceAuthorized?.getMyRepos()
    }

    override fun getReposByUser(login: String): Observable<List<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.getReposByUser(login)
        else
            return serviceNonAuthorized?.getReposByUser(login)
    }

    override fun getStarredByUser(login: String): Observable<List<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.getStarredByUser(login)
        else
            return serviceNonAuthorized?.getStarredByUser(login)
    }

    override fun getRepoByName(name: String): Observable<Repo>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchRepos(q: String): Call<SearchModel<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.searchRepos(q)
        else
            return serviceNonAuthorized?.searchRepos(q)
    }

}