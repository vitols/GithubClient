package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.presentation.model.Readme
import retrofit2.Call

/**
 * Created by admin on 22.03.2018.
 */
interface FileRequestInterface {
    fun getReadme(login: String, repoName: String): Call<Readme>?
}