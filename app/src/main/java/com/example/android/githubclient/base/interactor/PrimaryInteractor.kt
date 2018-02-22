package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.data.AbstractRepository
import com.example.android.githubclient.base.data.PrimaryRepository
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.CommonRequestInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by admin on 21.02.2018.
 */
class PrimaryInteractor() : CommonRequestInterface{

    private val repository: AbstractRepository = PrimaryRepository()

    override fun getRepos(): Observable<List<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsers(): Observable<List<User>> {
        return repository.getUsers()
                .subscribeOn(Schedulers.io())
                .filter { it != null && it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
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