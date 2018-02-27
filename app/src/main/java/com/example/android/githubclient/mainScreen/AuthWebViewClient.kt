package com.example.android.githubclient.mainScreen

import android.app.ProgressDialog
import android.webkit.WebViewClient
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.presenter.AuthPresenter

/**
 * Created by admin on 27.02.2018.
 */
class AuthWebViewClient(val spinner: ProgressDialog? = null, val presenter: AuthPresenter? = null) : WebViewClient() {

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
        spinner?.show()
    }

    override fun onPageFinished(view: WebView, url: String) {
        Log.e("onPageFinished", "here")
        super.onPageFinished(view, url)
        spinner?.dismiss()
    }

}