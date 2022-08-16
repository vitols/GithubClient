package com.example.android.githubclient.base.presentation.presenter

import com.example.android.githubclient.base.interactor.FileInteractor
import com.example.android.githubclient.base.interactor.RepoInteractor
import com.example.android.githubclient.base.presentation.model.Readme
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.view.RepoView
import com.example.android.githubclient.base.requests.FileRequestInterface
import com.example.android.githubclient.base.requests.ReposRequestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Base64
import com.example.android.githubclient.base.ConstValues

/**
 * Created by admin on 22.03.2018.
 */
class RepoPresenter(override var view: RepoView<*>?) : BasePresenter<RepoView<*>> {

    var interactorRepo: ReposRequestInterface = RepoInteractor()
    var interactorFile: FileRequestInterface = FileInteractor()


    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun getRepo(login: String, repoName: String) {
        interactorRepo.getRepo(login, repoName)?.enqueue(object : Callback<Repo> {
            override fun onFailure(call: Call<Repo>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Repo>?, response: Response<Repo>?) {
                if(response?.errorBody() != null)
                    view?.showError(response.errorBody().toString())
                else
                    view?.showRepo(response?.body() as Repo)
            }

        })
    }
    fun getFileContent(login: String, repoName: String) {
        interactorFile.getReadme(login, repoName)?.enqueue(object : Callback<Readme> {
            override fun onFailure(call: Call<Readme>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Readme>?, response: Response<Readme>?) {
                if(response?.code() == ConstValues.ResponseCode.NOT_FOUND)
                    view?.showReadme(null)
                if(response?.body()?.content != null)
                    view?.showReadme(String(Base64.decode(response.body()!!.content, Base64.DEFAULT)))
            }

        })
    }

}