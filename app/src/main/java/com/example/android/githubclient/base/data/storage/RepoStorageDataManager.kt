package com.example.android.githubclient.base.data.storage

import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 23.02.2018.
 */
class RepoStorageDataManager : RepoStorageDataManagerInterface {
    override fun getRepo(login: String, name: String): Call<Repo>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserReposSorted(login: String, sortParameter: String): Observable<List<Repo>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReposSortedBy(sort: String): Observable<List<Repo>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchRepos(q: String): Call<SearchModel<Repo>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStarredByUser(login: String): Observable<List<Repo>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRepos(): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReposByUser(login: String): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}