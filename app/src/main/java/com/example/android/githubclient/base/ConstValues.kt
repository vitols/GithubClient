package com.example.android.githubclient.base

/**
 * Created by admin on 23.02.2018.
 */
object ConstValues {
    object ParamNames {
        const val CLIENT_ID = "client_id"
        const val CLIENT_SECRET = "client_secret"
        const val ACCESS_TOKEN = "access_token"
        const val CODE = "code"
    }
    object ParamValues {
        const val CLIENT_ID = "c0a8a765ce30cfa59ae4"
        const val CLIENT_SECRET = "1e922f1bf7930765339084628be3964ee67fc592"
    }
    object Urls {
        const val BASE_URL = "https://github.com"
        const val BASE_API_URL = "https://api.github.com"
        const val GET_CODE_URL = "https://github.com/login/oauth/authorize"
        const val REDIRECT_URL = "https://github.com/vitols/GithubClient"
        const val REDIRECT_LOGOUT_URL = "https://github.com/"
        const val LOGOUT_URL = "https://github.com/logout"
    }
    object Path {
        const val GET_TOKEN_PATH = "/login/oauth/access_token?"
        const val GET_USER_PATH = "/user?"
        const val LOGOUT = "/logout"
    }
    object Errors {
        const val TITLE = "Error occurred"
        const val OK = "OK"
    }
}