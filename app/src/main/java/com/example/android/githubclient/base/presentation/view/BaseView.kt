package com.example.android.githubclient.base.presentation.view

/**
 * Created by admin on 22.02.2018.
 */

interface BaseView<P> {
    var presenter: P?
    fun showError(error: String)
}

