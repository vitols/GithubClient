package com.example.android.githubclient.base.data.storage

import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import io.reactivex.Observable

/**
 * Created by admin on 22.02.2018.
 */
class UserStorageDataManager : UserStorageDataManagerInterface {
    override fun getUserByLogin(login: String): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}