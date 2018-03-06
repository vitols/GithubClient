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
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.android.githubclient.base.ConstValues


/**
 * Created by admin on 19.02.2018.
 */
class FragmentProfile : Fragment(), UserView<UserPresenter>, SwipeRefreshLayout.OnRefreshListener {

    override var presenter: UserPresenter? = UserPresenter(this)

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var user: User? = null

    var image: CircleImageView? = null
    var name: TextView? = null
    var login: TextView? = null
    var bio: TextView? = null
    var company: TextView? = null
    var location: TextView? = null
    var locationField: LinearLayout? = null
    var email:TextView? = null
    var emailField: LinearLayout? = null

    var exitButton: ImageView? = null

    interface logOutInterface {
        fun showAuthScreen()
    }

    var logoutCallback: logOutInterface? = null

    override fun onRefresh() {
        presenter?.getMe()
    }

    override fun showMe() {
        user = LoginController.instance.user
        updateUser()
        swipeRefreshLayout?.isRefreshing = false
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
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showUserByLogin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            logoutCallback = context as logOutInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement Profile callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_screen_profile, container, false)

        presenter = UserPresenter(this)
        return view

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("FragmentProfile", "ViewCreated")

        swipeRefreshLayout = view?.findViewById(R.id.profile_swiperefresh_layout)
        setProgressViewOffset()
        swipeRefreshLayout?.setOnRefreshListener(this);

        image = view?.findViewById(R.id.screen_profile_avatar)
        var params = image?.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin += resources.getDimension(R.dimen.avatar_margin_top).toInt()
        image?.layoutParams = params

        exitButton = view?.findViewById(R.id.screen_profile_exit)
        params = exitButton?.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin += resources.getDimension(R.dimen.exit_button_margin_top).toInt()
        exitButton?.layoutParams = params
        exitButton?.setOnClickListener {
            val builder = AlertDialog.Builder(activity)

            builder.setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes",
                            DialogInterface.OnClickListener {
                                _,_ -> logoutCallback?.showAuthScreen()
                            })
                    .setNegativeButton("No",
                            DialogInterface.OnClickListener {
                                    dialog, _ -> dialog.cancel()
                    })

            val dialog = builder.create()
            dialog.show()
        }

        name = view?.findViewById(R.id.screen_profile_name)
        login = view?.findViewById(R.id.screen_profile_login)
        bio = view?.findViewById(R.id.screen_profile_bio)
        company = view?.findViewById(R.id.screen_profile_company)
        location = view?.findViewById(R.id.screen_profile_location)
        locationField = view?.findViewById(R.id.profile_field_location)
        email = view?.findViewById(R.id.screen_profile_email)
        emailField = view?.findViewById(R.id.profile_field_email)

        user = LoginController.instance.user
        if(user == null) {
            getProfileData()
        } else
            updateUser()
    }

    fun updateUser() {

        Glide.with(context)
                .load(user?.avatarUrl)
                .into(image)

        if(user?.name == null)
            name?.visibility = View.GONE
        else {
            name?.visibility = View.VISIBLE
            name?.text = user?.name
        }

        login?.setText(user?.login)

        if(user?.bio == null)
            bio?.visibility = View.GONE
        else {
            bio?.visibility = View.VISIBLE
            bio?.text = user?.bio
        }

        if(user?.company == null)
            company?.visibility = View.GONE
        else {
            company?.visibility = View.VISIBLE
            company?.text = user?.company.toString()
        }

        if(user?.location == null)
            locationField?.visibility = View.GONE
        else {
            locationField?.visibility = View.VISIBLE
            location?.text = user?.location
        }

        if(user?.email == null)
            emailField?.visibility = View.GONE
        else {
            emailField?.visibility = View.VISIBLE
            email?.text = user?.email.toString()
        }
    }
    fun setProgressViewOffset() {

        var typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        var actionBarSize = typedArray.getDimension(0, 0f)
        typedArray.recycle()

        var newStartOffset: Int = (actionBarSize * 1.1).toInt()
        var newEndOffset: Int = (actionBarSize * 2).toInt()

        swipeRefreshLayout?.setProgressViewOffset(false, newStartOffset, newEndOffset)
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
        swipeRefreshLayout?.post({swipeRefreshLayout?.isRefreshing = true})
        presenter?.getMe()
    }
}