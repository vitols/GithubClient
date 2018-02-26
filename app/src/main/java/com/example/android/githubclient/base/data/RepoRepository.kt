package com.example.android.githubclient.base.data

import com.example.android.githubclient.base.data.network.RepoNetworkDataManager
import com.example.android.githubclient.base.data.storage.RepoStorageDataManager
import com.example.android.githubclient.base.data.storage.RepoStorageDataManagerInterface
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable

/**
 * Created by admin on 23.02.2018.
 */
class RepoRepository : RepoRepositoryInterface {

    private val internetDM : ReposRequestInterface = RepoNetworkDataManager()
    private val storageDM : RepoStorageDataManagerInterface = RepoStorageDataManager()

    override fun getRepos(): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReposByUser(login: String): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRepoByName(name: String): Observable<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isOnline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isInStorage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}