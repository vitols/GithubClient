package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.data.network.services.UserService
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 21.02.2018.
 */
class UserNetworkDataManager : UsersRequestInterface {

    private var service: UserService? = null

    constructor() {
        service = RestApi.createService(UserService::class.java)
    }

    override fun getMe(): Call<User>? {
        return service?.getMe()
    }

    override fun getUsers(): Observable<List<User>>? {
        return service?.getUsers()
    }

    override fun getUserByLogin(login: String): Call<User>? {
        return service?.getUserByLogin(login)
    }

    override fun searchUsers(q: String): Call<SearchModel>? {
        var users = service?.searchUsers(q)
        return users
    }

    override fun getFollowersByLogin(login: String): Observable<List<User>>? {
        return service?.getFollowersByLogin(login)
    }

    override fun getFollowingByLogin(login: String): Observable<List<User>>? {
        return service?.getFollowingByLogin(login)
    }

}