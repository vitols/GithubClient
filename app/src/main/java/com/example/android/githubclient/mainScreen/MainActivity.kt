package com.example.android.githubclient.mainScreen

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.bumptech.glide.request.animation.ViewPropertyAnimation
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.R
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestAuth
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.utils.Prefs
import com.example.android.githubclient.mainScreen.mainFragments.FragmentAuth
import com.example.android.githubclient.mainScreen.mainFragments.FragmentProfile

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityParent, FragmentAuth.onLoggedIn, FragmentProfile.logOutInterface {

    var navigator: MainActivityNavigator = MainActivityNavigator(supportFragmentManager, R.id.main_activity_container)
    var sideBarHidden = false

    companion object {
        private val TAG = "MainActivity"
        val CODE_ME = 123
        val CODE_REPOS = 124
    }

    private fun setListeners() {
        main_sidebar_me.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_PROFILE) {

                if(!sideBarHidden)
                    hideSideBar()

                /*main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentRed, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));*/

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

                if(!sideBarHidden)
                    hideSideBar()

/*                main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentRed, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));*/


                Log.e("MainActivity", "Users Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_USERS)
            }
        }

        main_sidebar_repos.setOnClickListener {

            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_REPOS) {
                if(!sideBarHidden)
                    hideSideBar()

                /*main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentRed, null));*/

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
        supportActionBar?.show()
        Prefs.Companion.init(this)
        RestApi.init(LoginController.instance.authenticator)
        RestAuth.init()

        setListeners()

        floatingActionButton.setOnClickListener {
            if(sideBarHidden)
                openSideBar()
            else
                hideSideBar()
        }
        //hideSideBar()

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

    override fun showAuthScreen() {
        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH)
    }


    fun hideSideBar() {
        if(!sideBarHidden) {
            main_sidebar.animate()
                    .translationXBy(main_sidebar.width * 1f)
                    .setDuration(200)
                    .start()
            sideBarHidden = true
        }
    }
    fun openSideBar() {
        if(sideBarHidden) {
            main_sidebar.animate()
                    .translationXBy(main_sidebar.width * -1f)
                    .setDuration(200)
                    .start()
            sideBarHidden = false
        }
    }
}
