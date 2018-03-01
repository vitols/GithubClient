package com.example.android.githubclient.mainScreen

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.R
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestAuth
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

                main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.holo_red_dark, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.darker_gray, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.darker_gray, null));

                hideSideBar()

                if(LoginController.instance.isLoggedIn()) {
                    Log.e("OpenProfile", "LoggedIn")
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
                } else {
                    Log.e("OpenProfile", "notLoggedIn")
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                            MainActivityNavigator.Screens.SCREEN_PROFILE.getTag())
                }
            }
        }

        main_sidebar_users.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_USERS) {

                main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.darker_gray, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.holo_red_dark, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.darker_gray, null));

                hideSideBar()

                Log.e("MainActivity", "Users Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_USERS)
            }
        }

        main_sidebar_repos.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_REPOS) {

                main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.darker_gray, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.darker_gray, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        android.R.color.holo_red_dark, null));

                hideSideBar()

                if (LoginController.instance.isLoggedIn()) {
                    Log.e("OpenRepos", "LoggedIn")
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS)
                } else {
                    Log.e("OpenRepos", "notLoggedIn")
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                            MainActivityNavigator.Screens.SCREEN_REPOS.getTag())
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Prefs.Companion.init(this)
        RestApi.init(LoginController.instance.authenticator)
        RestAuth.init()

        setListeners()

        floatingActionButton.setOnClickListener {
            if(main_sidebar.visibility == View.INVISIBLE)
                openSideBar()
        }
        hideSideBar()

        navigator.openFirstFragment()
    }

    override fun action(screen: ScreenInterface?, data: Any?) {

    }

    override fun onBackPressed() {
        if(!navigator.backNavigation())
            super.onBackPressed()
    }

    override fun authFragmentCallback(tag: String) {
        if (tag == MainActivityNavigator.Screens.SCREEN_PROFILE.getTag())
            navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
        else if(tag == MainActivityNavigator.Screens.SCREEN_REPOS.getTag())
            navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS)
    }

    fun hideSideBar() {
        floatingActionButton.translationX += main_sidebar.width
        main_sidebar.visibility = View.INVISIBLE
    }
    fun openSideBar() {
        floatingActionButton.translationX -= main_sidebar.width
        main_sidebar.visibility = View.VISIBLE
    }
}
