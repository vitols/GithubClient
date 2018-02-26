package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.data.network.services.UserService
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable

/**
 * Created by admin on 21.02.2018.
 */
class UserNetworkDataManager : UsersRequestInterface {

    private var service: UserService? = null

    constructor() {
        service = RestApi.createService(UserService::class.java)
    }

    override fun getUsers(): Observable<List<User>> {
        return service!!.getUsers()
    }

    override fun getUserByLogin(login: String): Observable<User> {
        return service!!.getUserByLogin(login)
    }


}