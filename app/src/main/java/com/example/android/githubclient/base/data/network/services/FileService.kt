package com.example.android.githubclient.base.data.network.services

import com.example.android.githubclient.base.presentation.model.Readme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by admin on 22.03.2018.
 */
interface FileService {
    @GET("repos/{user}/{repo_name}/readme")
    fun getReadme(@Path("user") login: String, @Path("repo_name") repoName: String): Call<Readme>
}