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
        //add your credentials here
        const val CLIENT_ID = ""
        const val CLIENT_SECRET = ""
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
        const val SEARCH_REPOS = "/search/repositories"

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
        const val REDIRECTED_SCREEN = "REDIRECTED_SCREEN"
        const val ADD_BACK_NAVIGATION_KEY = "BACK_NAVIGATION"
        const val REPO_NAME_KEY = "REPO_NAME"
    }
    object SortValues {
        const val BY_CREATION = "created"
        const val BY_UPDATE = "updated"
        const val BY_PUSH = "pushed"
        const val BY_NAME = "full_name"
    }
    object UserData {
        const val NAME = "name"
        const val BIO = "bio"
        const val COMPANY = "company"
        const val LOCATION = "location"
        const val EMAIL = "email"
        const val BLOG = "blog"
        const val HIREABLE = "hireable"
    }
    object ResponseCode {
        const val UNPROCESSABLE_ENTITY = 422
        const val OK = 200
        const val NOT_FOUND = 404
    }
    object Scope {
        const val SCOPE_USER = "user"
        const val SCOPE_REPO = "repo"
    }
    object ErrorDialog {
        const val OK = "OK"
        const val TITLE = "ERROR OCCURED"
    }
    object EmptyList {
        const val NO_REPOS_ME = "You don't have any repositories yet."
        const val NO_REPOS_ANOTHER = " doesn't have any repositories yet."
        const val NO_STARRED_ME = "You don't have any starred repositories yet."
        const val NO_STARRED_ANOTHER = " doesn't have any starred repositories yet."
        const val NO_FOLLOWERS_ME = "You are not being followed by anybody."
        const val NO_FOLLOWERS_ANOTHER = " is not being followed by anybody."
        const val NO_FOLLOWING_ME = "You are not following anybody."
        const val NO_FOLLOWING_ANOTHER = " is not following anybody."
        const val NO_USERS = "We couldn’t find any users."
    }

}
