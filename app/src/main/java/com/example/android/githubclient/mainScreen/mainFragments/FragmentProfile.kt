package com.example.android.githubclient.mainScreen.mainFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.githubclient.R
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserPresenter
import com.example.android.githubclient.base.presentation.view.UserView
import de.hdodenhof.circleimageview.CircleImageView
import android.content.DialogInterface
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.android.githubclient.base.ConstValues
import kotlinx.android.synthetic.main.fragment_screen_profile.*


/**
 * Created by admin on 19.02.2018.
 */
class FragmentProfile : Fragment(), UserView<UserPresenter>, SwipeRefreshLayout.OnRefreshListener {

    override var presenter: UserPresenter? = UserPresenter(this)
    var user: User? = null

    interface FragmentProfileCallbackInterface {
        fun showAuthScreen()
        fun openRepos()
        fun openStarred()
        fun openFollowers()
        fun openFollowing()
    }
    var mainActivityCallback: FragmentProfileCallbackInterface? = null

    override fun onRefresh() {
        presenter?.getMe()
    }

    override fun showMe() {
        user = LoginController.instance.user
        updateUser()
        screen_profile_swiperefresh_layout?.isRefreshing = false
    }

    override fun showUserByLogin(user: User?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(error)
                .setTitle(ConstValues.Errors.TITLE)
                .setPositiveButton(ConstValues.Errors.OK,
                        DialogInterface.OnClickListener {
                            dialog, _ -> dialog.cancel()
                        })

        val dialog = builder.create()
        dialog.show()
        screen_profile_swiperefresh_layout?.isRefreshing = false
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_PROFILE"

        fun newInstance(): FragmentProfile {
            return FragmentProfile()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mainActivityCallback = context as FragmentProfileCallbackInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement Profile callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        presenter = UserPresenter(this)
        return inflater!!.inflate(R.layout.fragment_screen_profile, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen_profile_swiperefresh_layout.setOnRefreshListener(this);
        setSwiperefreshOffset()

        val topMargin = resources.getDimension(R.dimen.avatar_margin_top).toInt()
        val avatarParams = screen_profile_avatar.layoutParams as CoordinatorLayout.LayoutParams
        avatarParams.topMargin += topMargin
        screen_profile_avatar.layoutParams = avatarParams
        var exitButtonParams = screen_profile_exit.layoutParams as CoordinatorLayout.LayoutParams
        exitButtonParams.topMargin += topMargin
        screen_profile_exit.layoutParams = exitButtonParams

        user = LoginController.instance.user
        if(user == null) {
            getProfileData()
        } else
            updateUser()

        setOnClickListeners()
    }

    fun updateUser() {
        Glide.with(context)
                .load(user?.avatarUrl)
                .placeholder(R.drawable.base_avatar)
                .dontAnimate()
                .into(screen_profile_avatar)

        if(user?.name == null)
            screen_profile_name.visibility = View.GONE
        else {
            screen_profile_name.visibility = View.VISIBLE
            screen_profile_name.text = user?.name
        }

        screen_profile_login.setText(user?.login)

        if(user?.bio == null)
            screen_profile_bio.visibility = View.GONE
        else {
            screen_profile_bio.visibility = View.VISIBLE
            screen_profile_bio.text = user?.bio
        }

        if(user?.company == null)
            screen_profile_company.visibility = View.GONE
        else {
            screen_profile_company.visibility = View.VISIBLE
            screen_profile_company.text = user?.company.toString()
        }

        if(user?.location == null)
            profile_field_email.visibility = View.GONE
        else {
            profile_field_email.visibility = View.VISIBLE
            screen_profile_location.text = user?.location
        }

        if(user?.email == null)
            profile_field_email.visibility = View.GONE
        else {
            profile_field_email.visibility = View.VISIBLE
            screen_profile_email.text = user?.email.toString()
        }
    }
    fun setSwiperefreshOffset() {

        var typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        var actionBarSize = typedArray.getDimension(0, 0f)
        typedArray.recycle()

        var newStartOffset: Int = actionBarSize.toInt()
        var newEndOffset: Int = (actionBarSize * 2).toInt()

        screen_profile_swiperefresh_layout.setProgressViewOffset(false, newStartOffset, newEndOffset)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if(LoginController.instance.user == null && LoginController.instance.tokenReceived) {
                getProfileData()
            }
        }
    }

    fun getProfileData() {
        screen_profile_swiperefresh_layout.isRefreshing = true
        presenter?.getMe()
    }

    fun setOnClickListeners() {
        screen_profile_repos_button.setOnClickListener{ mainActivityCallback?.openRepos() }
        screen_profile_starred_button.setOnClickListener{ mainActivityCallback?.openStarred() }
        screen_profile_followers_button.setOnClickListener{ mainActivityCallback?.openFollowers() }
        screen_profile_following_button.setOnClickListener{ mainActivityCallback?.openFollowing() }
        screen_profile_exit.setOnClickListener {
            Log.e("button ", "was clicked")
            val builder = AlertDialog.Builder(activity)

            builder.setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes",
                            DialogInterface.OnClickListener {
                                _,_ -> mainActivityCallback?.showAuthScreen()
                            })
                    .setNegativeButton("No",
                            DialogInterface.OnClickListener {
                                dialog, _ -> dialog.cancel()
                            })

            val dialog = builder.create()
            dialog.show()
        }
    }
}