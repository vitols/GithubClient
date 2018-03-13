package com.example.android.githubclient.mainScreen

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestApiNonAuthorized
import com.example.android.githubclient.base.api.RestAuth
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.utils.Prefs
import com.example.android.githubclient.mainScreen.mainFragments.FragmentAuth
import com.example.android.githubclient.mainScreen.mainFragments.FragmentProfile
import com.example.android.githubclient.mainScreen.mainFragments.FragmentUsers
import com.example.android.githubclient.otherScreens.FragmentListAny
import com.example.android.githubclient.otherScreens.FragmentProfileUnauthorized

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainActivityParent,
        FragmentAuth.onLoggedIn,
        FragmentProfile.FragmentProfileCallbackInterface,
        FragmentUsers.FragmentListCallbackInterface {

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

                if (!backStackSizeIsEmpty())
                    clearBackStack()
                hideSideBar()

                if(LoginController.instance.isLoggedIn() || LoginController.instance.tokenReceived)
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
                else
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                            MainActivityNavigator.Screens.SCREEN_PROFILE.getTag())
            }
        }
        main_sidebar_users.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_USERS) {
                if (!backStackSizeIsEmpty())
                    clearBackStack()
                hideSideBar()
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_USERS)
            }
        }

        main_sidebar_repos.setOnClickListener {

            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_REPOS) {

                if (!backStackSizeIsEmpty())
                    clearBackStack()
                hideSideBar()

                if (LoginController.instance.isLoggedIn() || LoginController.instance.tokenReceived)
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS)
                else
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                            MainActivityNavigator.Screens.SCREEN_REPOS.getTag())

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
        RestApiNonAuthorized.init()

        setListeners()

        floatingActionButton.setOnClickListener {
            if(sideBarHidden)
                openSideBar()
            else
                hideSideBar()
        }
        if(LoginController.instance.isLoggedIn())
            navigator.openFirstFragment(MainActivityNavigator.Screens.SCREEN_PROFILE)
        else
            navigator.openFirstFragment(MainActivityNavigator.Screens.SCREEN_USERS)

    }

    override fun action(screen: ScreenInterface?, data: Any?) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    //!!!!!!!!!!
    override fun authFragmentCallback(tag: String) {
        if(navigator.getCurrentScreen()?.getLastScreen() != null) {
            Log.e("authFragmentCallback", "not null")
            navigator.showScreen(navigator.getLastShownScreen())
        }
        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
    }

    override fun showAuthScreen() {
        LoginController.instance.tryToLogOut = true
        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH, MainActivityNavigator.Screens.SCREEN_PROFILE)
    }


    fun hideSideBar(duration: Long = 200) {
        if(!sideBarHidden) {
            main_sidebar.animate()
                    .translationXBy(main_sidebar.width * 1f)
                    .setDuration(duration)
                    .start()
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

    override fun openRepos() {
        main_sidebar_repos.performClick()
    }

    override fun openScreenMe() {
        if(!backStackSizeIsEmpty())
            clearBackStack()
        main_sidebar_me.performClick()
    }

    private fun backStackSizeIsEmpty(): Boolean{
        return supportFragmentManager.backStackEntryCount == 0
    }

    private fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.executePendingTransactions()

    }

    override fun openProfileScreenByLogin(login: String) {
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.main_activity_container, FragmentProfileUnauthorized.newInstance(login))
                .addToBackStack(login)
                .hide(navigator.getCurrentFragment())
                .commit()
    }

    override fun openStarred() {
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.main_activity_container,
                        FragmentListAny.newInstance(
                                ConstValues.FragmentsData.STARRED_KEY,
                                LoginController.instance.user!!.login)
                )
                .addToBackStack(null)
                .hide(navigator.getCurrentFragment())
                .commit()
    }

    override fun openFollowers() {
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.main_activity_container,
                        FragmentListAny.newInstance(
                                ConstValues.FragmentsData.FOLLOWERS_KEY,
                                LoginController.instance.user!!.login)
                )
                .addToBackStack(null)
                .hide(navigator.getCurrentFragment())
                .commit()
    }

    override fun openFollowing() {
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.main_activity_container,
                        FragmentListAny.newInstance(
                                ConstValues.FragmentsData.FOLLOWING_KEY,
                                LoginController.instance.user!!.login)
                )
                .addToBackStack(null)
                .hide(navigator.getCurrentFragment())
                .commit()
    }



}
