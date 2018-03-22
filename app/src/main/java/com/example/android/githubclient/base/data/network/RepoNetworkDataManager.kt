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
    private var serviceNotAuthorized: RepoService? = null

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
            return serviceNotAuthorized?.getReposByUser(login)
    }

    override fun getStarredByUser(login: String): Observable<List<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.getStarredByUser(login)
        else
            return serviceNotAuthorized?.getStarredByUser(login)
    }

    override fun getRepo(login: String, name: String): Call<Repo>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.getRepo(login, name)
        else
            return serviceNotAuthorized?.getRepo(login, name)
    }

    override fun searchRepos(q: String): Call<SearchModel<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.searchRepos(q)
        else
            return serviceNotAuthorized?.searchRepos(q)
    }

    override fun getReposSortedBy(sort: String): Observable<List<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.getReposSorted(sort)
        else
            return serviceNotAuthorized?.getReposSorted(sort)
    }

    override fun getUserReposSorted(login: String, sortParameter: String): Observable<List<Repo>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthorized?.getUserReposSorted(login, sortParameter)
        else
            return serviceNotAuthorized?.getUserReposSorted(login, sortParameter)
    }


}