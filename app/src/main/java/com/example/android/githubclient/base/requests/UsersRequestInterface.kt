package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.model.User
import io.reactivex.Observable

/**
 * Created by admin on 22.02.2018.
 */
interface UsersRequestInterface {
    fun getUsers(): Observable<List<User>>
    fun getUserByLogin(): Observable<User>
}