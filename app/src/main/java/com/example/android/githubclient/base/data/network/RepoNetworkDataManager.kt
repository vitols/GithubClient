package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.data.network.services.RepoService
import com.example.android.githubclient.base.data.network.services.UserService
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable

/**
 * Created by admin on 23.02.2018.
 */
class RepoNetworkDataManager : ReposRequestInterface{

    private var service: RepoService? = null

    constructor() {
        service = RestApi.createService(RepoService::class.java)
    }

    override fun getRepos(): Observable<List<Repo>>? {
        return service?.getMyRepos()
    }

    override fun getReposByUser(login: String): Observable<List<Repo>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRepoByName(name: String): Observable<Repo>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}