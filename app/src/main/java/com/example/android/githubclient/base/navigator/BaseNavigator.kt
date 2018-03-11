package com.example.android.githubclient.base.navigator

/**
 * Created by admin on 20.02.2018.
 */
interface BaseNavigator {
    fun pushFragment(screen: ScreenInterface, data: Any? = null)
    fun pushFragmentWithoutUpdate(screen: ScreenInterface, data: Any? = null)
    fun openFirstFragment(screen: ScreenInterface)
    fun showScreen(screen: ScreenInterface, data: Any? = null)
}