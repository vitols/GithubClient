package com.example.android.githubclient.base.view

/**
 * Created by admin on 22.02.2018.
 */

interface BaseView<P> {
    var presenter: P?
    fun showError()
}

