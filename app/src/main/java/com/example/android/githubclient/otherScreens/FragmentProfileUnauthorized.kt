package com.example.android.githubclient.otherScreens

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.example.android.githubclient.R
import com.example.android.githubclient.R.layout.fragment_screen_profile
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserPresenter
import com.example.android.githubclient.base.presentation.view.UserView
import kotlinx.android.synthetic.main.fragment_screen_profile.*

/**
 * Created by admin on 10.03.2018.
 */
class FragmentProfileUnauthorized : Fragment(), UserView<UserPresenter> {
    override var presenter: UserPresenter? = null
    var progressBar: ProgressBar? = null
    var login: String = ""

    companion object {
        private val TAG = "TAG_FRAGMENT_PROFILE_ANOTHER"

        fun newInstance(login: String): Fragment {
            return FragmentProfileUnauthorized().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.LOGIN_KEY, login)
                }
            }
        }
    }
    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserByLogin(user: User?) {
        fillProfile(user)
        fragment_screen_profile_progressbar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = UserPresenter(this)
        return inflater!!.inflate(fragment_screen_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        fragment_screen_profile_progressbar.visibility = View.VISIBLE
        login = arguments.get(ConstValues.FragmentsData.LOGIN_KEY).toString()
        if(login == "")
            throw NullPointerException("Login is empty")

        screen_profile_toolbar.title = login + "'s profile"
        screen_profile_swiperefresh_layout.isEnabled = false

        var avatarParams = screen_profile_avatar.layoutParams as CoordinatorLayout.LayoutParams
        avatarParams.topMargin += resources.getDimension(R.dimen.avatar_margin_top).toInt()
        screen_profile_avatar.layoutParams = avatarParams
        var exitButtonParams = screen_profile_exit.layoutParams as CoordinatorLayout.LayoutParams
        exitButtonParams.behavior = null
        screen_profile_exit.layoutParams = exitButtonParams
        screen_profile_exit.visibility = View.GONE

        setOnClickListeners()

        presenter?.getUserByLogin(login)

    }

    fun fillProfile(user: User?) {

        Glide.with(context)
                .load(user?.avatarUrl)
                .placeholder(R.drawable.base_avatar)
                .dontAnimate()
                .into(screen_profile_avatar)

        if(user?.name == null)
            screen_profile_name.visibility = View.GONE
        else {
            screen_profile_name.visibility = View.VISIBLE
            screen_profile_name.text = user.name
        }

        screen_profile_login.text = user?.login

        if(user?.bio == null)
            screen_profile_bio.visibility = View.GONE
        else {
            screen_profile_bio.visibility = View.VISIBLE
            screen_profile_bio.text = user.bio
        }

        if(user?.company == null)
            screen_profile_company.visibility = View.GONE
        else {
            screen_profile_company.visibility = View.VISIBLE
            screen_profile_company.text = user.company.toString()
        }

        if(user?.location == null)
            profile_field_location.visibility = View.GONE
        else {
            profile_field_location.visibility = View.VISIBLE
            screen_profile_location.text = user.location
        }

        if(user?.email == null)
            profile_field_email.visibility = View.GONE
        else {
            profile_field_email.visibility = View.VISIBLE
            screen_profile_email.text = user.email.toString()
        }
    }

    fun setOnClickListeners() {
        screen_profile_repos_button.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.REPOS_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }
        screen_profile_starred_button.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.STARRED_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }
        screen_profile_followers_button.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.FOLLOWERS_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }
        screen_profile_following_button.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.FOLLOWING_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }

    }

}