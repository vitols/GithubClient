package com.example.android.githubclient.mainScreen.mainFragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.android.githubclient.R
import com.example.android.githubclient.base.presentation.presenter.AuthPresenter
import com.example.android.githubclient.base.presentation.view.AuthView
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.*
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.mainScreen.WebViewAuthClient
import android.view.animation.AnimationSet
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.item_repo.view.*


/**
 * Created by admin on 26.02.2018.
 */
class FragmentAuth : Fragment(), AuthView<AuthPresenter> {

    override var presenter: AuthPresenter? = null
    var progressBar: ProgressBar? = null
    var webView: WebView? = null

    var onLoggedInCallback: onLoggedIn? = null

    var window: Window? = null

    interface onLoggedIn {
        fun authFragmentCallback(tag: String);
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_AUTH"

        fun newInstance(data: Any? = null): Fragment {
            return FragmentAuth().apply {
                arguments = Bundle().apply {
                    putString("Screen", data.toString())
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            onLoggedInCallback = context as onLoggedIn
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement Auth callback")
        }
    }

    override fun showScreen() {
        var tag: String = arguments?.get("Screen").toString()
        if(!tag.isNullOrEmpty())
            onLoggedInCallback!!.authFragmentCallback(tag)
        else
            Log.e("showScreen", "argumentsIsEmpty")

    }

    override fun showError(error: String) {
        val builder = AlertDialog.Builder(context)

        builder.setMessage(error.toString())
                .setTitle("Error")
                .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_screen_auth, container, false)

        progressBar = view.findViewById<ProgressBar>(R.id.screen_auth_progress_bar)
        presenter = AuthPresenter(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view?.findViewById<WebView>(R.id.screen_auth_webview)

        webView?.settings?.javaScriptEnabled = true
        webView?.webViewClient = WebViewAuthClient(context, presenter, progressBar)

        if(!LoginController.instance.tryToLogOut)
            webView?.loadUrl(ConstValues.Urls.GET_CODE_URL + "?client_id=" + ConstValues.ParamValues.CLIENT_ID)
        else
            webView?.loadUrl(ConstValues.Urls.LOGOUT_URL)

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if(LoginController.instance.tryToLogOut) {
                webView?.loadUrl(ConstValues.Urls.LOGOUT_URL)
            }


        }
    }

}