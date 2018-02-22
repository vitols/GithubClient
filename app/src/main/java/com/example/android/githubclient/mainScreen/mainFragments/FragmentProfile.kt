package com.example.android.githubclient.mainScreen.mainFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R
import com.example.android.githubclient.base.model.User
import com.example.android.githubclient.base.presenter.UserPresenter
import com.example.android.githubclient.base.view.UserView

/**
 * Created by admin on 19.02.2018.
 */
class FragmentProfile : Fragment(), UserView<UserPresenter> {
    override var presenter: UserPresenter? = UserPresenter(this)

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUsers(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserByLogin(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_PROFILE"

        fun newInstance(): FragmentProfile {
            return FragmentProfile()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_screen_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}