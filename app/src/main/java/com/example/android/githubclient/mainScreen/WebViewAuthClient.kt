package com.example.android.githubclient.mainScreen

import android.app.ActionBar
import android.app.Activity
import android.app.ProgressDialog
import android.webkit.WebViewClient
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.presenter.AuthPresenter
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.widget.FrameLayout
import android.widget.Toolbar
import com.example.android.githubclient.R


/**
 * Created by admin on 27.02.2018.
 */
class WebViewAuthClient(val spinner: ProgressDialog? = null,
                        val presenter: AuthPresenter? = null) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        super.shouldOverrideUrlLoading(view, url)

        if (url.startsWith(ConstValues.Urls.REDIRECT_URL)) {
            val urls = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Log.e("WebViewAuthClient", "Call presenter")
            presenter?.getAccessToken(urls[1])
            return true
        }
        return false
    }

    override fun onReceivedError(view: WebView, errorCode: Int,
                        description: String, failingUrl: String) {
        Log.e("onReceivedError", "here")

        super.onReceivedError(view, errorCode, description, failingUrl)
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        Log.e("onPageStarted", "here")

        super.onPageStarted(view, url, favicon)
        spinner?.show()
    }

    override fun onPageFinished(view: WebView, url: String) {
        Log.e("onPageFinished", "here")
        super.onPageFinished(view, url)
        spinner?.dismiss()
    }
}