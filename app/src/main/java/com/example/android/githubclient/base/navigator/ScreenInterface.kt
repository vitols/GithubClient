package com.example.android.githubclient.base.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


/**
 * Created by admin on 20.02.2018.
 */
interface ScreenInterface{
    var data: Any?

    fun getTag(): String
    fun getLastScreen(): ScreenInterface?
    fun createFragment(data: Any? = null): Fragment
    fun setAnimation(fragmentManager: FragmentManager): FragmentTransaction = fragmentManager.beginTransaction()
}