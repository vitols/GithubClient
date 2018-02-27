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
    object Api {
        const val BASE_URL = "https://api.github.com"
        const val GET_CODE_URL = "https://github.com/login/oauth/authorize"
        const val GET_TOKEN_URL = "https://github.com/login/oauth/access_token"
    }

    object Auth {
        const val CLIENT_ID = "c0a8a765ce30cfa59ae4"
        const val CLIENT_SECRET = "8f923109671140ddcd55e6de26b4d936ebeecb93"
    }
}