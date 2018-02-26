package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.data.RepoRepository
import com.example.android.githubclient.base.data.RepoRepositoryInterface
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable

/**
 * Created by admin on 23.02.2018.
 */
class RepoInteractor : ReposRequestInterface {

    val repository: RepoRepositoryInterface = RepoRepository()

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