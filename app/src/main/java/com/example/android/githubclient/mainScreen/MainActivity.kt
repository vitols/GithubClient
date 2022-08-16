package com.example.android.githubclient.mainScreen

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.R
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.api.RestApiNonAuthorized
import com.example.android.githubclient.base.api.RestAuth
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.utils.Prefs
import com.example.android.githubclient.mainScreen.fragments.FragmentAuth
import com.example.android.githubclient.mainScreen.fragments.FragmentProfileAuthorized
import com.example.android.githubclient.mainScreen.fragments.FragmentUsers
import com.google.android.material.navigation.NavigationBarView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_screen_repos.*
import kotlinx.android.synthetic.main.fragment_screen_users.*

class MainActivity : AppCompatActivity(),
        MainActivityParent,
        FragmentAuth.onLogInListener,
        FragmentProfileAuthorized.onLogOutListenter,
        FragmentUsers.FragmentListListener,
        NavigationBarView.OnItemSelectedListener {

    var navigator: MainActivityNavigator = MainActivityNavigator(supportFragmentManager, R.id.main_activity_container)
    var sideBarHidden = false

    companion object {
        val TAG = "ACTIVITY_MAIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()
        Prefs.init(this)
        RestApi.init(LoginController.instance.authenticator)
        RestAuth.init()
        RestApiNonAuthorized.init()
        main_bottombar.setOnItemSelectedListener(this)

        if(LoginController.instance.isLoggedIn()) {
            navigator.openFirstFragment(MainActivityNavigator.Screens.SCREEN_PROFILE)
        }
        else
            navigator.openFirstFragment(MainActivityNavigator.Screens.SCREEN_USERS)

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.main_sidebar_me ->
                if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_PROFILE) {
                    if (!backStackSizeIsEmpty())
                        clearBackStack()
                    if(LoginController.instance.isLoggedIn() || LoginController.instance.tokenReceived)
                        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE, false)
                    else
                        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                                MainActivityNavigator.Screens.SCREEN_PROFILE.getTag())
                } else {
                    if (!backStackSizeIsEmpty())
                        clearBackStack()
                }
            R.id.main_sidebar_repos ->
                if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_REPOS) {
                    if (!backStackSizeIsEmpty())
                        clearBackStack()
                    if (LoginController.instance.isLoggedIn() || LoginController.instance.tokenReceived)
                        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS, false)
                    else
                        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH,
                                MainActivityNavigator.Screens.SCREEN_REPOS.getTag())
                } else {
                    if (backStackSizeIsEmpty()) {
                        if (screen_repos_container != null)
                            screen_repos_container.smoothScrollToPosition(0)
                    } else
                        clearBackStack()
                }
            R.id.main_sidebar_users ->
                if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_USERS) {
                    if (!backStackSizeIsEmpty())
                        clearBackStack()
                    navigator.showScreen(MainActivityNavigator.Screens.SCREEN_USERS)
                } else {
                    if (backStackSizeIsEmpty()) {
                        if (screen_users_container != null)
                            screen_users_container.smoothScrollToPosition(0)
                    } else
                        clearBackStack()
                }
        }
        return true
    }

    override fun action(screen: ScreenInterface?, data: Any?) {

    }

    override fun onBackPressed() {
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit()
        super.onBackPressed()
    }

    //!!!!!!!!!!
    override fun callbackOnLoggedIn(tag: String) {
        if(navigator.getCurrentScreen()?.getLastScreen() != null)
            navigator.showScreen(navigator.getLastShownScreen())
        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
    }

    override fun callbackOnLoggedOut() {
        LoginController.instance.tryToLogOut = true
        navigator.showScreen(MainActivityNavigator.Screens.SCREEN_AUTH, MainActivityNavigator.Screens.SCREEN_PROFILE)
    }
    override fun hideBar() {
        hideSideBar()
    }

    override fun hideBarCallback() {
        hideSideBar(500)
    }

    override fun openProfileScreenByLogin(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.main_activity_container, fragment)
                .addToBackStack(null)
                .hide(navigator.getCurrentFragment())
                .commit()
    }

    private fun hideSideBar(duration: Long = 200) {
        if(!sideBarHidden) {

            Log.e("before",  main_bottombar.translationX.toString())
            main_bottombar.animate()
                    .translationXBy(main_bottombar.width * 1f)
                    .setDuration(duration)
                    .start()
            sideBarHidden = true
            Log.e("after",  main_bottombar.translationX.toString())
        }
    }

    private fun openSideBar(duration: Long = 200) {
        if(sideBarHidden) {
            main_bottombar.animate()
                    .translationXBy(main_bottombar.width * -1f)
                    .setDuration(duration)
                    .start()
            sideBarHidden = false
        }
    }

    private fun backStackSizeIsEmpty(): Boolean{
        return supportFragmentManager.backStackEntryCount == 0
    }

    private fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.executePendingTransactions()
    }
    private fun isTopFragment(tag: String): Boolean {
        val lastInd = supportFragmentManager.backStackEntryCount - 1
        val backEntry = supportFragmentManager.getBackStackEntryAt(lastInd)
        return if(tag == backEntry.name) true else false
    }
}
