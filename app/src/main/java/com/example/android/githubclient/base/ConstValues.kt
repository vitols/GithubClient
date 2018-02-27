package com.example.android.githubclient.base

/**
 * Created by admin on 23.02.2018.
 */
object ConstValues {
    object Prefs {
        const val SCOPES = "SCOPES"
        const val NOTE = "NOTE"
        const val CLIENT_ID = "CLIENT_ID"
        const val CLIENT_SECRET = "CLIENT_SECRET"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
    object Urls {
        const val BASE_URL = "https://github.com"
        const val BASE_API_URL = "https://api.github.com"
        const val GET_CODE_URL = "https://github.com/login/oauth/authorize"
        const val GET_TOKEN_URL = "https://github.com/login/oauth/access_token"
        const val REDIRECT_URL = "https://github.com/vitols/GithubClient"
    }

    object Auth {
        const val CLIENT_ID = "c0a8a765ce30cfa59ae4"
        const val CLIENT_SECRET = "1e922f1bf7930765339084628be3964ee67fc592"
    }
}