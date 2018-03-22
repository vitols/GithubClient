package com.example.android.githubclient.mainScreen.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.example.android.githubclient.R
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.model.User
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.util.Log
import android.widget.TextView
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.fragments.FragmentProfileAbstract
import com.example.android.githubclient.mainScreen.adapters.RepoDelegate
import com.example.android.githubclient.otherScreens.FragmentEditProfile
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_screen_profile.*


/**
 * Created by admin on 19.02.2018.
 */
class FragmentProfileAuthorized : FragmentProfileAbstract(),
        FragmentEditProfile.EditProfileApplyChangesListener,
        SwipeRefreshLayout.OnRefreshListener {

    interface onLogOutListenter {
        fun callbackOnLoggedOut()
        fun hideBar()
    }

    var user: User? = null
    var onLoggedOutCallback: onLogOutListenter? = null

    companion object {
        val TAG = "TAG_FRAGMENT_PROFILE"
        fun newInstance(addNavigationButton: Any? = null): FragmentProfileAuthorized {
            return FragmentProfileAuthorized().apply {
                arguments = Bundle().apply { putBoolean(ConstValues.FragmentsData.ADD_BACK_NAVIGATION_KEY,
                        if (addNavigationButton != null) addNavigationButton as Boolean else true) }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onLoggedOutCallback = context as onLogOutListenter
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement onLogOutListener")
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen_profile_swiperefresh_layout.setOnRefreshListener(this)
        setSwiperefreshOffset()
        setToolbarItems()

        val exitButtonParams = screen_profile_exit.layoutParams as CoordinatorLayout.LayoutParams
        exitButtonParams.topMargin += resources.getDimension(R.dimen.exit_button_margin_top).toInt()
        screen_profile_exit.layoutParams = exitButtonParams

        adapter?.manager?.addDelegate(RepoDelegate(activity,
                {_, _, name ->
                    activity.supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .add(R.id.main_activity_container, FragmentRepository.newInstance(user!!.login, name))
                        .addToBackStack(FragmentRepository.TAG)
                        .commit()}))

        user = LoginController.instance.user
        if(user != null) {
            setOnClickListeners(user!!.login)
            fillProfile(user)
            screen_profile_progress_bar.visibility = View.VISIBLE
            presenter?.getMyRepos(ConstValues.SortValues.BY_CREATION, user!!.login)
        } else {
            getProfileData()
        }
        onLoggedOutCallback!!.hideBar()

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if(LoginController.instance.user == null && LoginController.instance.tokenReceived) {
                getProfileData()
                onLoggedOutCallback!!.hideBar()
            }
        }
    }

    override fun onRefresh() {
        presenter?.getMe()
        screen_profile_repos_container.visibility = View.GONE
    }

    override fun updateMe() {
        user = LoginController.instance.user
        fillProfile(user)
    }

    override fun showMe() {
        user = LoginController.instance.user
        screen_profile_progress_bar.visibility = View.VISIBLE
        presenter?.getMyRepos(ConstValues.SortValues.BY_CREATION, user!!.login)
        fillProfile(user)
        screen_profile_swiperefresh_layout?.isRefreshing = false
    }


    override fun showUserByLogin(user: User?) {

    }

    override fun onApplyChanges(name: String, bio: String, company: String, location: String,
                                email: String, blog: String, hireable: Boolean) {

        activity.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(activity.supportFragmentManager.findFragmentByTag(FragmentEditProfile.TAG))
                .commit()
        presenter?.updateUser(name, bio, company, location, email, blog, hireable)

    }

    override fun setOnClickListeners(login: String) {
        super.setOnClickListeners(login)
        screen_profile_exit.setOnClickListener {
            AlertDialog.Builder(activity)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", {_,_ -> onLoggedOutCallback?.callbackOnLoggedOut() })
                    .setNegativeButton("No",{ dialog, _ -> dialog.cancel() })
                    .create()
                    .show()
        }
    }

    private fun setSwiperefreshOffset() {
        val typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val actionBarSize = typedArray.getDimension(0, 0f)
        typedArray.recycle()

        val newStartOffset: Int = actionBarSize.toInt()
        val newEndOffset: Int = (actionBarSize * 2).toInt()

        screen_profile_swiperefresh_layout.setProgressViewOffset(false, newStartOffset, newEndOffset)
    }


    fun getProfileData() {
        screen_profile_swiperefresh_layout.isRefreshing = true
        presenter?.getMe()
    }

    override fun setToolbarItems() {
        super.setToolbarItems()
        screen_profile_toolbar.inflateMenu(R.menu.menu_main)

        val item = screen_profile_toolbar
                .menu
                .getItem(0)
        item.setOnMenuItemClickListener {
            activity.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.main_activity_container, FragmentEditProfile.newInstance(
                        screen_profile_name.text.toString(),
                        screen_profile_bio.text.toString(),
                        screen_profile_company.text.toString(),
                        screen_profile_location.text.toString(),
                        screen_profile_email.text.toString(),
                        user?.blog ?: "",
                        user?.hireable ?: false
                ), FragmentEditProfile.TAG)
                .addToBackStack(null)
                .commit();
             true}
    }
}