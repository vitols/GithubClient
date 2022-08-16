package com.example.android.githubclient.base.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


/**
 * Created by admin on 20.02.2018.
 */
abstract class BaseTransactionManager(protected val fragmentManager: FragmentManager, protected val containerId: Int) {

    open fun getBackStackSize(): Int {
        return fragmentManager.backStackEntryCount
    }

    abstract fun getCurrentScreen(): ScreenInterface?
    abstract fun getCurrentFragment(): Fragment
    abstract fun showFirstFragment(screen: ScreenInterface, data: Any? = null)
    abstract fun showFragment(screen: ScreenInterface, data: Any? = null)
    abstract fun popFragment()
    abstract fun clear()
    abstract fun backNavigation(): Boolean
    abstract fun updateUi(screen: ScreenInterface?)
    abstract fun updateChildUi(screen: ScreenInterface?, data: Any? = null)
}