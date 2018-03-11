package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.interactor.RepoInteractor
import com.example.android.githubclient.base.interactor.UserInteractor
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.view.AnyListView
import com.example.android.githubclient.base.presentation.view.BaseView

/**
 * Created by admin on 10.03.2018.
 */
class AnyListPresenter(override var view: AnyListView<*>?) : BasePresenter<AnyListView<*>> {

    val interactorUser = UserInteractor()
    val interactorRepo = RepoInteractor()

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun getListRepos(login: String) {
        interactorRepo.getReposByUser(login)?.subscribe(
                {it -> view?.showListRepos(it)},
                { e -> view?.showError(e.localizedMessage)}
        )
    }
    fun getListStarred(login: String) {
        interactorRepo.getStarredByUser(login)?.subscribe(
                {it -> view?.showListStarred(it)},
                { e -> view?.showError(e.localizedMessage)}
        )
    }
    fun getListFollowers(login: String) {
        interactorUser.getFollowersByLogin(login)?.subscribe(
                {it -> view?.showListFollowers(it)},
                { e -> view?.showError(e.localizedMessage)}
        )
    }
    fun getListFollowing(login: String) {
        interactorUser.getFollowingByLogin(login)?.subscribe(
                {it -> view?.showListFollowing(it)},
                { e -> view?.showError(e.localizedMessage)}
        )
    }

}