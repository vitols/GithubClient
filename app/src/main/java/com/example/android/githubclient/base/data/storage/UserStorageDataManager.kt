package com.example.android.githubclient.base.data.storage

import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.model.User
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 22.02.2018.
 */
class UserStorageDataManager : UserStorageDataManagerInterface {
    override fun updateUser(name: String, bio: String, company: String, location: String, email: String, blog: String, hireable: Boolean): Call<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFollowersByLogin(login: String): Observable<List<User>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFollowingByLogin(login: String): Observable<List<User>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchUsers(q: String): Call<SearchModel<User>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMe(): Call<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByLogin(login: String): Call<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}