package com.example.android.githubclient.base.navigator
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * Created by admin on 20.02.2018.
 */
interface ScreenInterface {
    var data: Any?

    fun getTag(): String
    fun getLastScreen(): ScreenInterface?
    fun createFragment(): Fragment
    fun setAnimation(fragmentManager: FragmentManager): FragmentTransaction = fragmentManager.beginTransaction()
}