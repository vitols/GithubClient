package com.example.android.githubclient.base.presentation.view

import android.support.v4.app.Fragment
import com.example.android.githubclient.base.navigator.ScreenInterface

/**
 * Created by admin on 26.02.2018.
 */
interface AuthView<P> : BaseView<P> {
    fun showScreen(tag: String)
}