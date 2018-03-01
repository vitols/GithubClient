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
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import com.example.android.githubclient.R


/**
 * Created by admin on 27.02.2018.
 */
class AuthWebViewClient(val spinner: ProgressDialog? = null,
                        val presenter: AuthPresenter? = null,
                        val activity: Activity? = null) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        super.shouldOverrideUrlLoading(view, url)

        if (url.startsWith(ConstValues.Urls.REDIRECT_URL)) {
            val urls = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Log.e("AuthWebViewClient", "Call presenter")
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
        setFullscreen(activity)
        spinner?.show()
    }

    override fun onPageFinished(view: WebView, url: String) {
        Log.e("onPageFinished", "here")
        super.onPageFinished(view, url)
        spinner?.dismiss()
    }

    fun setFullscreen(activity: Activity?) {

        var appBar: AppBarLayout? = activity?.findViewById(R.id.main_activity_appbar)
        appBar?.setExpanded(false, true)

        var flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            flags = flags or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        activity?.window?.decorView?.systemUiVisibility = flags

    }

    fun exitFullscreen(activity: Activity?) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

}