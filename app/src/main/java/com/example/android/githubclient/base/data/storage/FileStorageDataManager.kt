package com.example.android.githubclient.base.data.storage

import com.example.android.githubclient.base.presentation.model.Readme
import com.example.android.githubclient.base.requests.FileRequestInterface
import retrofit2.Call

/**
 * Created by admin on 22.03.2018.
 */
class FileStorageDataManager : FileRequestInterface {
    override fun getReadme(login: String, repoName: String): Call<Readme>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}