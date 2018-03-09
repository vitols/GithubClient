package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.Repo
import retrofit2.http.GET
import io.reactivex.Observable

/**
 * Created by admin on 09.03.2018.
 */
interface RepoService {
    @GET(ConstValues.Path.GET_REPOS)
    fun getMyRepos() : Observable<List<Repo>>
}