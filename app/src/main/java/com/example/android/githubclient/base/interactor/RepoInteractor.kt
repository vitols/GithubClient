package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.data.RepoRepository
import com.example.android.githubclient.base.data.RepoRepositoryInterface
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.requests.ReposRequestInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * Created by admin on 23.02.2018.
 */
class RepoInteractor : ReposRequestInterface {

    val repository: RepoRepositoryInterface = RepoRepository()

    override fun getRepos(): Observable<List<Repo>>? {
        return repository.getRepos()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun getReposByUser(login: String): Observable<List<Repo>>? {
        return repository.getReposByUser(login)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun getRepoByName(name: String): Observable<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStarredByUser(login: String): Observable<List<Repo>>? {
        return repository.getStarredByUser(login)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun searchRepos(q: String): Call<SearchModel<Repo>>? {
        return repository.searchRepos(q)
    }

    override fun getReposSortedBy(sort: String): Observable<List<Repo>>? {
        return repository.getReposSortedBy(sort)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserReposSorted(login: String, sortParameter: String): Observable<List<Repo>>? {
        return repository.getUserReposSorted(login, sortParameter)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

}