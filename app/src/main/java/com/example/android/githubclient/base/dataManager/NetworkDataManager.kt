package com.example.android.githubclient.base.dataManager

import com.example.android.githubclient.base.model.Repo
import com.example.android.githubclient.base.model.User
import com.example.android.githubclient.base.requests.CommonRequestInterface
import io.reactivex.Observable

/**
 * Created by admin on 21.02.2018.
 */
class NetworkDataManager() : CommonRequestInterface {
    override fun getRepos(): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReposByUser(login: String): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByLogin(): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRepoByName(name: String): Observable<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}