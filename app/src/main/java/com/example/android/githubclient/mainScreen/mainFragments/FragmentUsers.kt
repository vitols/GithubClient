package com.example.android.githubclient.mainScreen.mainFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserPresenter
import com.example.android.githubclient.base.presentation.view.UserView
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


/**
 * Created by admin on 20.02.2018.
 */
class FragmentUsers : Fragment(), UserView<UserPresenter> {
    override var presenter: UserPresenter? = null
    var adapter: Any? = null

    override fun showError(error: String) {
        val builder = AlertDialog.Builder(activity)

/*        builder.setMessage(error.toString() + R.string.alert_dialog_message)
                .setTitle(R.string.alert_dialog_title)
                .setPositiveButton(R.string.alert_dialog_button, DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })*/

        val dialog = builder.create()
        dialog.show()
    }

    override fun showUsers(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserByLogin(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_USERS"

        fun newInstance(): Fragment {
            return FragmentUsers()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = UserPresenter(this)
        return inflater!!.inflate(R.layout.fragment_screen_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}