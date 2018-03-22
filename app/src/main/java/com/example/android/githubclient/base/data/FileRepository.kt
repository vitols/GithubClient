package com.example.android.githubclient.base.data

import com.example.android.githubclient.base.data.network.FileNetworkDataManager
import com.example.android.githubclient.base.data.storage.FileStorageDataManager
import com.example.android.githubclient.base.presentation.model.Readme
import com.example.android.githubclient.base.requests.FileRequestInterface
import retrofit2.Call

/**
 * Created by admin on 22.03.2018.
 */
class FileRepository : FileRepositoryInterface {

    var internetDM: FileRequestInterface? = null
    var storageDM: FileRequestInterface? = null

    constructor() {
        internetDM = FileNetworkDataManager()
        storageDM = FileStorageDataManager()
    }

    override fun isOnline() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isInStorage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReadme(login: String, repoName: String): Call<Readme>? {
        return internetDM?.getReadme(login, repoName)
    }

}