package com.example.android.githubclient.mainScreen.mainFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.base.presentation.presenter.AuthPresenter
import com.example.android.githubclient.base.presentation.view.AuthView
import kotlinx.android.synthetic.main.fragment_screen_auth.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.android.githubclient.base.api.RequestContainer


/**
 * Created by admin on 26.02.2018.
 */
class FragmentAuth : Fragment(), AuthView<AuthPresenter> {

    override var presenter: AuthPresenter? = null

    var onLoggedInCallback: onLoggedIn? = null

    interface onLoggedIn {
        fun authFragmentCallback(tag: String);
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_AUTH"

        fun newInstance(data: String? = null): Fragment {
            Log.e("FragmentAuth", "HERE")
            return FragmentUsers().apply {
                arguments = Bundle().apply {
                    putString("Screen", data)
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

    override fun showScreen(tag: String) {
        onLoggedInCallback!!.authFragmentCallback(tag)
    }

    override fun showError(error: String) {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(error.toString())
                .setTitle("Error")
                .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = AuthPresenter(this)
        Log.e("FragmentAuth", "onCreate")
        return inflater!!.inflate(R.layout.fragment_screen_auth, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen_auth_button_login.setOnClickListener {
            if (!screen_auth_login.text.isEmpty() && !screen_auth_password.text.isEmpty())
                presenter!!.tryToLogIn(RequestContainer(screen_auth_login.text.toString(),
                        screen_auth_password.text.toString()), Bundle().getString("Screen"))
        }
    }
}