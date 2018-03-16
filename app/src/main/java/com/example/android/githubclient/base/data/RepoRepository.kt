package com.example.android.githubclient.base.data

import com.example.android.githubclient.base.data.network.RepoNetworkDataManager
import com.example.android.githubclient.base.data.storage.RepoStorageDataManager
import com.example.android.githubclient.base.data.storage.RepoStorageDataManagerInterface
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 23.02.2018.
 */
class RepoRepository : RepoRepositoryInterface {

    private val internetDM : ReposRequestInterface = RepoNetworkDataManager()
    private val storageDM : RepoStorageDataManagerInterface = RepoStorageDataManager()

    override fun getRepos(): Observable<List<Repo>>? {
        return internetDM.getRepos()
    }

    override fun getReposByUser(login: String): Observable<List<Repo>>? {
        return internetDM.getReposByUser(login)
    }

    override fun getRepoByName(name: String): Observable<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStarredByUser(login: String): Observable<List<Repo>>? {
        return internetDM.getStarredByUser(login)
    }

    override fun searchRepos(q: String): Call<SearchModel<Repo>>? {
        return internetDM.searchRepos(q)
    }

    override fun isOnline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isInStorage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun getReposSortedBy(sort: String): Observable<List<Repo>>? {
        return internetDM.getReposSortedBy(sort)
    }

    override fun getUserReposSorted(login: String, sortParameter: String): Observable<List<Repo>>? {
        return internetDM.getUserReposSorted(login, sortParameter)
    }


}