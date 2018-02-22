package com.example.android.githubclient.base.navigator

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * Created by admin on 20.02.2018.
 */
abstract class StoreAndReuseTransactionManager(fragmentManager : FragmentManager, containerId: Int)
    : BaseTransactionManager(fragmentManager, containerId) {

    private val tags: HashSet<ScreenInterface?> = HashSet()
    private var curScreen: ScreenInterface? = null

    override fun showFragment(screen: ScreenInterface, data: Any?) {

        if (curScreen === screen)
            return

        if (tags.contains(screen)) {
            screen.setAnimation(fragmentManager)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .hide(fragmentManager.findFragmentByTag(curScreen?.getTag()))
                    .show(fragmentManager.findFragmentByTag(screen.getTag()))
                    .commit()
        } else {
            tags.add(screen)
            screen.setAnimation(fragmentManager)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(containerId, screen.createFragment(), screen.getTag())
                    .hide(fragmentManager.findFragmentByTag(curScreen?.getTag()))
                    .commit()
        }

        curScreen = screen
    }

    override fun showFirstFragment(screen: ScreenInterface, data: Any?) {
        curScreen = screen

        tags.add(screen)

        screen.setAnimation(fragmentManager)
                .add(containerId, screen.createFragment(), screen.getTag())
                .commit()
    }

    override fun popFragment() {
        fragmentManager.popBackStack()
    }

    override fun getCurrentScreen(): ScreenInterface? {
        return curScreen
    }

    override fun getCurrentFragment(): Fragment {
        return fragmentManager.findFragmentByTag(getCurrentScreen()?.getTag())
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
                        .show(fragmentManager.findFragmentByTag(curScreen?.getTag()))
                        .commit()

                true
            }
            else ->
                false
        }
    }

}