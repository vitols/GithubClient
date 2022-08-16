package com.example.android.githubclient.base.navigator

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


/**
 * Created by admin on 20.02.2018.
 */
abstract class StoreAndReuseTransactionManager(fragmentManager : FragmentManager, containerId: Int)
    : BaseTransactionManager(fragmentManager, containerId) {

    private val tags: HashSet<ScreenInterface?> = HashSet()
    private var curScreen: ScreenInterface? = null

    @SuppressLint("WrongConstant")
    override fun showFragment(screen: ScreenInterface, data: Any?) {
        if (curScreen === screen)
            return
        var previousScreen = curScreen
        if (tags.contains(screen)) {
            /*Log.e("showScreenAuthInTags", screen.getTag())*/
            screen.setAnimation(fragmentManager)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .hide(fragmentManager.findFragmentByTag(curScreen?.getTag())!!)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .show(fragmentManager.findFragmentByTag(screen.getTag())!!)
                    .commit()
        } else {
            /*Log.e("showScreenAuthNotInTags", screen.getTag())*/
            tags.add(screen)
            screen.setAnimation(fragmentManager)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(containerId, screen.createFragment(data), screen.getTag())
                    .hide(fragmentManager.findFragmentByTag(curScreen?.getTag())!!)
                    .commit()
        }
        curScreen = screen
    }

    override fun showFirstFragment(screen: ScreenInterface, data: Any?) {
        curScreen = screen

        tags.add(screen)

        screen.setAnimation(fragmentManager)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(containerId, screen.createFragment(data), screen.getTag())
                .commit()
    }

    override fun popFragment() {
        fragmentManager.popBackStack()
    }

    override fun getCurrentScreen(): ScreenInterface? {
        return curScreen
    }

    override fun getCurrentFragment(): Fragment {
        return fragmentManager.findFragmentByTag(getCurrentScreen()?.getTag())!!
    }

    override fun clear() {
        tags.clear()

        for (i: Int in 0..getBackStackSize()) {
            fragmentManager.popBackStack()
        }
    }

    override fun backNavigation(): Boolean {
        return when {
            tags.isEmpty() ->
                false
            curScreen?.getLastScreen() == null ->
                true
            curScreen?.getLastScreen() != null -> {
                curScreen= curScreen?.getLastScreen()

                tags.remove(curScreen)

                curScreen!!.setAnimation(fragmentManager)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .show(fragmentManager.findFragmentByTag(curScreen?.getTag())!!)
                        .commit()

                true
            }
            else ->
                false
        }
    }

}