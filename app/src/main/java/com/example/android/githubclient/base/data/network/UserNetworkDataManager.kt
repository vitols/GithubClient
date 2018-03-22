package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestApiNonAuthorized
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.data.network.services.UserService
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.model.UserUpdate
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 21.02.2018.
 */
class UserNetworkDataManager : UsersRequestInterface {

    private var serviceAuthoried: UserService? = null
    private var serviceNonAuthorized: UserService? = null

    constructor() {
        serviceAuthoried = RestApi.createService(UserService::class.java)
        serviceNonAuthorized = RestApiNonAuthorized.createService(UserService::class.java)
    }

    override fun getMe(): Call<User>? {
        return serviceAuthoried?.getMe()
    }

    override fun getUsers(): Observable<List<User>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthoried?.getUsers()
        else
            return serviceNonAuthorized?.getUsers()
    }

    override fun getUserByLogin(login: String): Call<User>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthoried?.getUserByLogin(login)
        else
            return serviceNonAuthorized?.getUserByLogin(login)
    }

    override fun searchUsers(q: String): Call<SearchModel<User>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthoried?.searchUsers(q)
        else
            return serviceNonAuthorized?.searchUsers(q)
    }

    override fun getFollowersByLogin(login: String): Observable<List<User>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthoried?.getFollowersByLogin(login)
        else
            return serviceNonAuthorized?.getFollowersByLogin(login)
    }

    override fun getFollowingByLogin(login: String): Observable<List<User>>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthoried?.getFollowingByLogin(login)
        else
            return serviceNonAuthorized?.getFollowingByLogin(login)
    }

    override fun updateUser(name: String, bio: String, company: String, location: String, email: String, blog: String, hireable: Boolean): Call<User>? {
        return serviceAuthoried?.updateUser(UserUpdate(name, bio, company, location, email, blog, hireable))
    }

}