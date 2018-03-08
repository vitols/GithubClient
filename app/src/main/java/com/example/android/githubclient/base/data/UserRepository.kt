package com.example.android.githubclient.base.data

import com.example.android.githubclient.base.data.network.UserNetworkDataManager
import com.example.android.githubclient.base.data.storage.UserStorageDataManager
import com.example.android.githubclient.base.data.storage.UserStorageDataManagerInterface
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.UsersRequestInterface
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by admin on 23.02.2018.
 */
class UserRepository : UserRepositoryInterface {

    private val networkDM : UsersRequestInterface = UserNetworkDataManager()
    private val storageDM : UserStorageDataManagerInterface = UserStorageDataManager()

    override fun isOnline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isInStorage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMe(): Call<User>? {
        return networkDM.getMe()
    }

    override fun getUsers(): Observable<List<User>>? {
        return networkDM.getUsers()
    }

    override fun getUserByLogin(login: String): Call<User>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}