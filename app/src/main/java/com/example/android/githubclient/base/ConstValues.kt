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
        const val CLIENT_SECRET = "2048ff053b489a5d94b273cde0679fb239467711"
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
        const val GET_TOKEN = "/login/oauth/access_token?"
        const val LOGOUT = "/logout"

        const val GET_USER = "/user"
        const val GET_USERS = "/users"
        const val SEARCH_USERS = "/search/users"

        const val GET_REPOS = "/user/repos"

    }
    object Errors {
        const val TITLE = "Error occurred"
        const val OK = "OK"
    }
    object FragmentsData {
        const val DATA = "DATA"
        const val LOGIN_KEY = "LOGIN"
        const val REPOS_KEY = "REPOS"
        const val STARRED_KEY = "STARRED"
        const val FOLLOWERS_KEY = "FOLLOWERS"
        const val FOLLOWING_KEY = "FOLLOWING"
    }
}