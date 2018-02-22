package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.interactor.PrimaryInteractor
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.requests.CommonRequestInterface
import com.example.android.githubclient.base.requests.UsersRequestInterface
import com.example.android.githubclient.base.presentation.view.UserView
import io.reactivex.Observable

/**
 * Created by admin on 21.02.2018.
 */
class UserPresenter(override var view: UserView<*>?) : BasePresenter<UserView<*>>, UsersRequestInterface {
    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val interactor: CommonRequestInterface = PrimaryInteractor()

    override fun getUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByLogin(): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}