package com.example.android.githubclient.base.requests

import com.example.android.githubclient.base.model.Repo
import com.example.android.githubclient.base.model.User
import io.reactivex.Observable

/**
 * Created by admin on 21.02.2018.
 */
interface CommonRequestInterface : ReposRequestInterface, UsersRequestInterface {
}