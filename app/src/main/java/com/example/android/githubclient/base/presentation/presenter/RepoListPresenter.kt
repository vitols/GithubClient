package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.interactor.RepoInteractor
import com.example.android.githubclient.base.presentation.view.RepoListView

/**
 * Created by admin on 09.03.2018.
 */
class RepoListPresenter(override var view: RepoListView<*>?) : BasePresenter<RepoListView<*>> {

    val interactor = RepoInteractor()

    fun getUsers() {
        interactor.getRepos()
                ?.subscribe({it -> view?.showRepos(it)},
                            {e -> view?.showError(e.localizedMessage)})
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}