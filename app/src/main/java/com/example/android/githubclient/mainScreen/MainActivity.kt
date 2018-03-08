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
import com.example.android.githubclient.mainScreen.mainFragments.FragmentUsers

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainActivityParent,
        FragmentAuth.onLoggedIn,
        FragmentProfile.logOutInterface,
        FragmentUsers.firstFragmentCreated {

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
                Log.e("Main AccessToken = ", LoginController.instance.tokenReceived.toString())
                hideSideBar()

                /*main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentRed, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));*/

                if(LoginController.instance.isLoggedIn() || LoginController.instance.tokenReceived) {
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
                hideSideBar()

                /*main_sidebar_me.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_users.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentBlack, null));
                main_sidebar_repos.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.transparentRed, null));*/

                if (LoginController.instance.isLoggedIn() || LoginController.instance.tokenReceived) {
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

        navigator.openFirstFragment()
    }

    override fun action(screen: ScreenInterface?, data: Any?) {

    }

    override fun onBackPressed() {
        if(!navigator.backNavigation())
            super.onBackPressed()
    }

    override fun authFragmentCallback(tag: String) {
        if (tag == MainActivityNavigator.Screens.SCREEN_PROFILE.getTag()) {
            navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
        }
        else if(tag == MainActivityNavigator.Screens.SCREEN_REPOS.getTag()) {
            navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS)
        }
    }

    override fun showAuthScreen() {
        LoginController.instance.tryToLogOut = true
        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH, MainActivityNavigator.Screens.SCREEN_PROFILE)
    }


    fun hideSideBar(duration: Long = 200) {
        if(!sideBarHidden) {
            Log.e("hideSideBar", "here")
            Log.e("sideBar.x", main_sidebar.x.toString())
            main_sidebar.animate()
                    .translationXBy(main_sidebar.width * 1f)
                    .setDuration(duration)
                    .start()
            Log.e("sideBar.x", main_sidebar.x.toString())
            sideBarHidden = true
        }
    }
    fun openSideBar(duration: Long = 200) {
        if(sideBarHidden) {
            main_sidebar.animate()
                    .translationXBy(main_sidebar.width * -1f)
                    .setDuration(duration)
                    .start()
            sideBarHidden = false
        }
    }

    override fun hideBarCallback() {
        hideSideBar(500)
    }
}
