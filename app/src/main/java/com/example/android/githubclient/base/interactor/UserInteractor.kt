package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.data.UserRepository
import com.example.android.githubclient.base.data.UserRepositoryInterface
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * Created by admin on 23.02.2018.
 */
class UserInteractor: UsersRequestInterface {

    var repository: UserRepositoryInterface? = null

    constructor() {
        repository = UserRepository()
    }

    override fun getMe(): Call<User>? {
        return repository?.getMe()
    }

    override fun getUsers(): Observable<List<User>>? {
        return repository?.getUsers()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserByLogin(login: String): Call<User>? {
        return repository?.getUserByLogin(login)
    }

    override fun searchUsers(q: String): Call<SearchModel<User>>? {
        return repository?.searchUsers(q)
    }

    override fun getFollowersByLogin(login: String): Observable<List<User>>? {
        return repository?.getFollowersByLogin(login)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFollowingByLogin(login: String): Observable<List<User>>? {
        return repository?.getFollowingByLogin(login)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }

}