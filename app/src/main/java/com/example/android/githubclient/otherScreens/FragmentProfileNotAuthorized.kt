package com.example.android.githubclient.otherScreens

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.mainScreen.adapters.RepoDelegate
import com.example.android.githubclient.base.fragments.FragmentProfileAbstract
import com.example.android.githubclient.mainScreen.fragments.FragmentRepository
import kotlinx.android.synthetic.main.fragment_screen_profile.*

/**
 * Created by admin on 10.03.2018.
 */
class FragmentProfileNotAuthorized : FragmentProfileAbstract() {
    var login: String = ""

    companion object {
        private val TAG = "TAG_FRAGMENT_PROFILE_ANOTHER"

        fun newInstance(login: String, addNavigationButton: Any? = null): Fragment {
            return FragmentProfileNotAuthorized().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.LOGIN_KEY, login)
                    putBoolean(ConstValues.FragmentsData.ADD_BACK_NAVIGATION_KEY,
                            if (addNavigationButton != null) addNavigationButton as Boolean else true)
                }
            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        fragment_screen_profile_progressbar.visibility = View.VISIBLE
        super.onViewCreated(view, savedInstanceState)
        login = arguments.get(ConstValues.FragmentsData.LOGIN_KEY).toString()
        if(login.isEmpty())
            throw NullPointerException("Login is empty in " + TAG)

        setOnClickListeners(login)
        setToolbarItems()
        screen_profile_swiperefresh_layout.isEnabled = false

        val exitButtonParams = screen_profile_exit.layoutParams as CoordinatorLayout.LayoutParams
        exitButtonParams.behavior = null
        screen_profile_exit.layoutParams = exitButtonParams
        screen_profile_exit.visibility = View.GONE
        adapter?.manager?.addDelegate(RepoDelegate(activity,
                {_, _, name ->
                    activity.supportFragmentManager
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .add(R.id.main_activity_container, FragmentRepository.newInstance(login, name))
                            .addToBackStack(FragmentRepository.TAG)
                            .commit()}))

        screen_profile_progress_bar.visibility = View.VISIBLE
        presenter?.getUserByLogin(login)
        presenter?.getReposByLogin(login, ConstValues.SortValues.BY_CREATION)

    }

    override fun showUserByLogin(user: User?) {
        fillProfile(user)
        fragment_screen_profile_progressbar.visibility = View.GONE
    }

    override fun setToolbarItems() {
        super.setToolbarItems()
        screen_profile_toolbar.title = login + "'s profile"
    }

    override fun updateMe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}