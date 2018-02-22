package com.example.android.githubclient.base.presenter


/**
 * Created by admin on 21.02.2018.
 */
interface BasePresenter<V> {
    var view: V?
    fun onStop()
}