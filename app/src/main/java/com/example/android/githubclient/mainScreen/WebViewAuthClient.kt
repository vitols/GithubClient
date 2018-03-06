package com.example.android.githubclient.mainScreen

import android.app.ActionBar
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
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
import android.webkit.RenderProcessGoneDetail
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toolbar
import com.example.android.githubclient.R


/**
 * Created by admin on 27.02.2018.
 */
class WebViewAuthClient(val context: Context? = null,
                        val presenter: AuthPresenter? = null,
                        val progressBar: ProgressBar? = null
                        ) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        super.shouldOverrideUrlLoading(view, url)
        if(url == ConstValues.Urls.REDIRECT_LOGOUT_URL && LoginController.instance.tryToLogOut) {
            LoginController.instance.logOut()
            view.loadUrl(ConstValues.Urls.GET_CODE_URL + "?client_id=" + ConstValues.ParamValues.CLIENT_ID)
            Toast.makeText(context, "You have logged out successfully", Toast.LENGTH_SHORT).show()
        }

        if (url.startsWith(ConstValues.Urls.REDIRECT_URL)) {
            val urls = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (!LoginController.instance.tryToLogOut)
                presenter?.getAccessToken(urls[1])
            return true
        }
        return false
    }

    override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        super.onReceivedError(view, errorCode, description, failingUrl)
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progressBar?.visibility = View.VISIBLE
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        progressBar?.visibility = View.INVISIBLE
    }

}