package com.example.android.githubclient.mainScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.android.githubclient.MainApp
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.R
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestApiAuth
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.utils.Prefs
import com.example.android.githubclient.mainScreen.mainFragments.FragmentAuth

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityParent, FragmentAuth.onLoggedIn {

    var navigator: MainActivityNavigator = MainActivityNavigator(supportFragmentManager, R.id.main_activity_container)

    companion object {
        private val TAG = "MainActivity"
        val CODE_ME = 123
        val CODE_REPOS = 124
    }

    private fun setListeners() {
        main_sidebar_me.setOnClickListener {

            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_PROFILE) {
                if(LoginController.instance.isLoggedIn()) {
                Log.e("MainActivity", "Profile Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
                } else {
                Log.e("MainActivity", "Profile AUTH start")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                        MainActivityNavigator.Screens.SCREEN_PROFILE.getTag())
                }
            }
        }

        main_sidebar_users.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_USERS) {
                Log.e("MainActivity", "Users Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_USERS)
            }
        }

        main_sidebar_repos.setOnClickListener {
            if (LoginController.instance.isLoggedIn() &&
                    navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_REPOS) {
                Log.e("MainActivity", "Repos Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS)
/*
                 main_sidebar_me.background =
                 main_sidebar_users.background =
                 main_sidebar_repos.background =*/
            } else {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Prefs.Companion.init(this)
        RestApi.init(LoginController.instance.authenticator)
        RestApiAuth.init()

        setListeners()
        navigator.openFirstFragment()
    }

    override fun action(screen: ScreenInterface?, data: Any?) {

    }

    override fun onBackPressed() {
        if(!navigator.backNavigation())
            super.onBackPressed()
    }

    override fun authFragmentCallback(tag: String) {
        navigator.showFragmentByTag(tag)
    }
}
