package com.example.android.githubclient.base.interactor

import com.example.android.githubclient.base.data.FileRepository
import com.example.android.githubclient.base.data.FileRepositoryInterface
import com.example.android.githubclient.base.presentation.model.Readme
import com.example.android.githubclient.base.requests.FileRequestInterface
import retrofit2.Call

/**
 * Created by admin on 22.03.2018.
 */
class FileInteractor : FileRequestInterface{
    var fileRepository: FileRepositoryInterface? = null

    constructor() {
        fileRepository = FileRepository()
    }

    override fun getReadme(login: String, repoName: String): Call<Readme>? {
        return fileRepository?.getReadme(login, repoName)
    }

}