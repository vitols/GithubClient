package com.example.android.githubclient.mainScreen.fragments

import android.content.Context
import android.os.Bundle
import com.example.android.githubclient.R
import com.example.android.githubclient.base.presentation.presenter.AuthPresenter
import com.example.android.githubclient.base.presentation.view.AuthView
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.mainScreen.WebViewAuthClient
import kotlinx.android.synthetic.main.fragment_screen_auth.*


/**
 * Created by admin on 26.02.2018.
 */
class FragmentAuth : Fragment(), AuthView<AuthPresenter> {

    override var presenter: AuthPresenter? = null
    var onLoggedInCallback: onLogInListener? = null

    interface onLogInListener {
        fun callbackOnLoggedIn(tag: String);
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_AUTH"
        fun newInstance(data: Any? = null): Fragment {
            return FragmentAuth().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.REDIRECTED_SCREEN, data.toString())
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onLoggedInCallback = context as onLogInListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement onLogInListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = AuthPresenter(this)
        return inflater.inflate(R.layout.fragment_screen_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        screen_auth_webview.settings?.javaScriptEnabled = true
        screen_auth_webview.webViewClient = WebViewAuthClient(context, presenter, screen_auth_progress_bar)

        if(!LoginController.instance.tryToLogOut)
            screen_auth_webview.loadUrl(ConstValues.Urls.GET_CODE_URL +
                    "?scope=" + ConstValues.Scope.SCOPE_USER + "%20" + ConstValues.Scope.SCOPE_REPO  +
                    "&client_id=" + ConstValues.ParamValues.CLIENT_ID)
        else
            screen_auth_webview.loadUrl(ConstValues.Urls.LOGOUT_URL)

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && LoginController.instance.tryToLogOut) {
                screen_auth_webview.loadUrl(ConstValues.Urls.LOGOUT_URL)
            }
    }

    override fun showScreen() {
        val screen = arguments?.get(ConstValues.FragmentsData.REDIRECTED_SCREEN).toString()
        if(onLoggedInCallback != null)
            onLoggedInCallback?.callbackOnLoggedIn(screen)
        else
            throw NullPointerException("FragmentAuth callback is null")

    }

    override fun showError(error: String) {
        AlertDialog.Builder(requireContext())
                .setMessage(error)
                .setTitle(ConstValues.ErrorDialog.TITLE)
                .setPositiveButton(ConstValues.ErrorDialog.OK, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
    }


}