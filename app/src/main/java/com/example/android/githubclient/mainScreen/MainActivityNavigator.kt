package com.example.android.githubclient.mainScreen

import androidx.fragment.app.FragmentManager
import com.example.android.githubclient.base.navigator.*
import com.example.android.githubclient.mainScreen.fragments.FragmentAuth
import com.example.android.githubclient.mainScreen.fragments.FragmentProfileAuthorized
import com.example.android.githubclient.mainScreen.fragments.FragmentRepos
import com.example.android.githubclient.mainScreen.fragments.FragmentUsers

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
            override fun createFragment(data: Any?) = FragmentProfileAuthorized.newInstance(data)
            override fun setAnimation(fragmentManager: FragmentManager) = fragmentManager.beginTransaction()
        },
        SCREEN_USERS {
            override var data: Any? = null
            override fun getTag() = "SCREEN_USERS"
            override fun getLastScreen() = null
            override fun createFragment(data: Any?) = FragmentUsers.newInstance()
            override fun setAnimation(fragmentManager: FragmentManager) = fragmentManager.beginTransaction()
        },
        SCREEN_REPOS {
            override var data: Any? = null
            override fun getTag() = "SCREEN_REPOS"
            override fun getLastScreen() = null
            override fun createFragment(data: Any?) = FragmentRepos.newInstance(data)
            override fun setAnimation(fragmentManager: FragmentManager) = fragmentManager.beginTransaction()
        },
        SCREEN_AUTH {
            override var data: Any? = null
            override fun getTag() = "SCREEN_AUTH"
            override fun getLastScreen() = null
            override fun createFragment(data: Any?) = FragmentAuth.newInstance(data)
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

    override fun openFirstFragment(screen: ScreenInterface) {
        showFirstFragment(screen, false)
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

    fun getLastShownScreen(): ScreenInterface {
        return getCurrentScreen()!!.getLastScreen()!!
    }

}