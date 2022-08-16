package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.interactor.RepoInteractor
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.SearchModel
import com.example.android.githubclient.base.presentation.view.RepoListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 09.03.2018.
 */
class RepoListPresenter(override var view: RepoListView<*>?) : BasePresenter<RepoListView<*>> {

    val interactor = RepoInteractor()

    fun getRepos() {
        interactor.getRepos()
                ?.subscribe({it -> view?.showRepos(it)},
                            {e -> view?.showError(e.localizedMessage)})
    }
    fun searchRepos(q: String) {
        interactor.searchRepos(q)?.enqueue(object : Callback<SearchModel<Repo>> {

            override fun onResponse(call: Call<SearchModel<Repo>>?, response: Response<SearchModel<Repo>>?) {
                try {
                    var responseModel = response?.body() as SearchModel<Repo>
                    view?.showRepos(responseModel.data!!)
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<SearchModel<Repo>>?, t: Throwable?) {
                view?.showError(t?.localizedMessage.toString())
            }
        })
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}