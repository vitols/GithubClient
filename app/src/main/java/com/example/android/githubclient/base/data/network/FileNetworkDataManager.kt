package com.example.android.githubclient.base.data.network

import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestApiNonAuthorized
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.data.network.services.FileService
import com.example.android.githubclient.base.presentation.model.Readme
import com.example.android.githubclient.base.requests.FileRequestInterface
import retrofit2.Call

/**
 * Created by admin on 22.03.2018.
 */
class FileNetworkDataManager : FileRequestInterface{

    private var serviceAuthoried: FileService? = null
    private var serviceNonAuthorized: FileService? = null

    constructor() {
        serviceAuthoried = RestApi.createService(FileService::class.java)
        serviceNonAuthorized = RestApiNonAuthorized.createService(FileService::class.java)
    }
    override fun getReadme(login: String, repoName: String): Call<Readme>? {
        if(LoginController.instance.isLoggedIn())
            return serviceAuthoried?.getReadme(login, repoName)
        else
            return serviceNonAuthorized?.getReadme(login, repoName)
    }
}