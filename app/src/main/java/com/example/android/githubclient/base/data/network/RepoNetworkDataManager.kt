package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable

/**
 * Created by admin on 23.02.2018.
 */
class RepoNetworkDataManager : ReposRequestInterface{
    override fun getRepos(): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReposByUser(login: String): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRepoByName(name: String): Observable<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}