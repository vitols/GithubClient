package com.example.android.githubclient.base.dataManager

import com.example.android.githubclient.base.requests.CommonRequestInterface

/**
 * Created by admin on 22.02.2018.
 */
abstract class AbstractRepository : CommonRequestInterface {
    open fun isOnline() {

    }
}