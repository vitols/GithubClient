package com.example.android.githubclient.mainScreen.mainFragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.githubclient.R
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserPresenter
import com.example.android.githubclient.base.presentation.view.UserView
import android.content.DialogInterface
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.mainScreen.adapterDelegates.RepoProfileDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_profile.*


/**
 * Created by admin on 19.02.2018.
 */
class FragmentProfile : Fragment(), UserView<UserPresenter>, SwipeRefreshLayout.OnRefreshListener {

    override var presenter: UserPresenter? = UserPresenter(this)
    var user: User? = null
    var adapter: DelegationAdapter<Any>? = null

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
        screen_profile_repos_container.visibility = View.GONE
        screen_profile_progress_bar.visibility = View.VISIBLE
        presenter?.getMyRepos(ConstValues.SortValues.BY_CREATION)
    }

    override fun showMe() {
        user = LoginController.instance.user
        updateUser()
        screen_profile_swiperefresh_layout?.isRefreshing = false
    }

    override fun showRepos(listOfRepos: List<Repo>) {
        screen_profile_progress_bar.visibility = View.GONE
        if(listOfRepos.size == 0) {
            screen_profile_empty_repos.visibility = View.VISIBLE
            return
        }
        try{
            screen_profile_empty_repos.visibility = View.GONE
            screen_profile_repos_container.visibility = View.VISIBLE
            adapter?.replaceAllItems(ArrayList<Any>(listOfRepos.subList(0, 3)))
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun showUserByLogin(user: User?) {

    }

    override fun showError(error: String) {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(error)
                .setTitle(ConstValues.Errors.TITLE)
                .setPositiveButton(ConstValues.Errors.OK,{ dialog, _ -> dialog.cancel() })

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
        adapter = DelegationAdapter<Any>()
        return inflater!!.inflate(R.layout.fragment_screen_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screen_profile_swiperefresh_layout.setOnRefreshListener(this)
        setSwiperefreshOffset()

        val topMargin = resources.getDimension(R.dimen.avatar_margin_top).toInt()
        val avatarParams = screen_profile_avatar.layoutParams as CoordinatorLayout.LayoutParams
        avatarParams.topMargin += topMargin
        screen_profile_avatar.layoutParams = avatarParams

        val exitButtonParams = screen_profile_exit.layoutParams as CoordinatorLayout.LayoutParams
        exitButtonParams.topMargin += resources.getDimension(R.dimen.exit_button_margin_top).toInt()
        screen_profile_exit.layoutParams = exitButtonParams

        screen_profile_repos_container.adapter = adapter
        screen_profile_repos_container.layoutManager = LinearLayoutManager(context)
        screen_profile_repos_container.addItemDecoration(ItemDecorator(context, false))
        ViewCompat.setNestedScrollingEnabled(screen_profile_repos_container, false)
        adapter?.manager?.addDelegate(RepoProfileDelegate(activity, {}))

        user = LoginController.instance.user
        if(user != null) {
            updateUser()
            screen_profile_progress_bar.visibility = View.VISIBLE
            presenter?.getMyRepos(ConstValues.SortValues.BY_CREATION)
        } else {
            getProfileData()
        }


        setOnClickListeners()
    }

    fun updateUser() {
        Glide.with(context)
                .load(user?.avatarUrl)
                .placeholder(R.drawable.base_avatar)
                .dontAnimate()
                .into(screen_profile_avatar)

        if(user?.name == null)
            screen_profile_name.text = getString(R.string.empty_text)
        else {
            screen_profile_name.text = user?.name
        }

        screen_profile_login.setText(user?.login)

        if(user?.bio == null)
            screen_profile_bio.text = getString(R.string.empty_text)
        else {
            screen_profile_bio.text = user?.bio
        }

        if(user?.company == null)
            screen_profile_company.text = getString(R.string.empty_text)
        else {
            screen_profile_company.text = user?.company.toString()
        }

        if(user?.location == null)
            screen_profile_location.text = getString(R.string.empty_text)
        else {
            screen_profile_location.text = user?.location
        }

        if(user?.email == null)
            screen_profile_email.text = getString(R.string.empty_text)
        else {
            screen_profile_email.text = user?.email.toString()
        }

    }
    fun setSwiperefreshOffset() {

        val typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val actionBarSize = typedArray.getDimension(0, 0f)
        typedArray.recycle()

        val newStartOffset: Int = actionBarSize.toInt()
        val newEndOffset: Int = (actionBarSize * 2).toInt()

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
        presenter?.getMyRepos(ConstValues.SortValues.BY_CREATION)
    }

    fun setOnClickListeners() {
        screen_profile_repos.setOnClickListener{ mainActivityCallback?.openRepos() }
        screen_profile_starred.setOnClickListener{ mainActivityCallback?.openStarred() }
        screen_profile_followers.setOnClickListener{ mainActivityCallback?.openFollowers() }
        screen_profile_following.setOnClickListener{ mainActivityCallback?.openFollowing() }
        screen_profile_exit.setOnClickListener {
            AlertDialog.Builder(activity)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", {_,_ -> mainActivityCallback?.showAuthScreen() })
                    .setNegativeButton("No",{ dialog, _ -> dialog.cancel() })
                    .create()
                    .show()
        }
    }
}