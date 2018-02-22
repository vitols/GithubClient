package com.example.android.githubclient.mainScreen

import android.support.v4.app.FragmentManager
import com.example.android.githubclient.base.navigator.*
import com.example.android.githubclient.mainScreen.mainFragments.FragmentProfile
import com.example.android.githubclient.mainScreen.mainFragments.FragmentRepos
import com.example.android.githubclient.mainScreen.mainFragments.FragmentUsers

/**
 * Created by admin on 20.02.2018.
 */
class MainActivityNavigator(fragmentManager: FragmentManager, containerId: Int) :
        StoreAndReuseTransactionManager(fragmentManager, containerId), BaseNavigator {

    enum class Screens : ScreenInterface {
        SCREEN_PROFILE {
            override var data: Any? = null
            override fun getTag() = "SCREEN_PROFILE"
            override fun getLastScreen() = null
            override fun createFragment() = FragmentProfile.newInstance()
            override fun setAnimation(fragmentManager: FragmentManager) = fragmentManager.beginTransaction()
        },
        SCREEN_USERS {
            override var data: Any? = null
            override fun getTag() = "SCREEN_USERS"
            override fun getLastScreen() = null
            override fun createFragment() = FragmentUsers.newInstance()
            override fun setAnimation(fragmentManager: FragmentManager) = fragmentManager.beginTransaction()
        },
        SCREEN_REPOS {
            override var data: Any? = null
            override fun getTag() = "SCREEN_REPOS"
            override fun getLastScreen() = null
            override fun createFragment() = FragmentRepos.newInstance()
            override fun setAnimation(fragmentManager: FragmentManager) = fragmentManager.beginTransaction()
        }
    }

    /* BaseNavigator funcs */

    override fun pushFragment(screen: ScreenInterface, data: Any?) {
        showFragment(screen, data)
        updateUi(screen)
    }

    override fun pushFragmentWithoutUpdate(screen: ScreenInterface, data: Any?) {
        showFragment(screen, data)
    }

    override fun openFirstFragment() {
        showFirstFragment(Screens.SCREEN_PROFILE, null)
    }

    override fun showScreen(screen: ScreenInterface, data: Any?) {
        pushFragment(screen, data)
    }

    /*StoreAndReuseTransactionManager funcs*/

    override fun backNavigation(): Boolean {
            try {
                if ((getCurrentFragment() as SimpleChild).onBackPressed()) {
                    return true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
    }

    override fun updateUi(screen: ScreenInterface?) {
    }

    override fun updateChildUi(screen: ScreenInterface?, data: Any?) {
        try {
            (getCurrentFragment() as SimpleParent).action(screen, data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}