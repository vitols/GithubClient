package com.example.android.githubclient.otherScreens

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.example.android.githubclient.R
import com.example.android.githubclient.R.layout.fragment_screen_profile
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.adapters.AdapterDelegate
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserPresenter
import com.example.android.githubclient.base.presentation.view.UserView
import com.example.android.githubclient.mainScreen.adapterDelegates.RepoProfileDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_profile.*

/**
 * Created by admin on 10.03.2018.
 */
class FragmentProfileUnauthorized : Fragment(), UserView<UserPresenter> {

    override var presenter: UserPresenter? = null
    var login: String = ""
    var adapter: DelegationAdapter<Any>? = null

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
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(error)
                .setTitle(ConstValues.Errors.TITLE)
                .setPositiveButton(ConstValues.Errors.OK,{ dialog, _ -> dialog.cancel() })

        val dialog = builder.create()
        dialog.show()
        screen_profile_swiperefresh_layout?.isRefreshing = false
    }

    override fun showMe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserByLogin(user: User?) {
        fillProfile(user)
        fragment_screen_profile_progressbar.visibility = View.GONE
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

            val list: ArrayList<Any>
            if(listOfRepos.size > 3)
                list = ArrayList<Any>(listOfRepos.subList(0, 3))
            else
                list = listOfRepos as ArrayList<Any>

            adapter?.replaceAllItems(list)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = UserPresenter(this)
        adapter = DelegationAdapter<Any>()
        return inflater!!.inflate(fragment_screen_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        fragment_screen_profile_progressbar.visibility = View.VISIBLE
        login = arguments.get(ConstValues.FragmentsData.LOGIN_KEY).toString()
        if(login == "")
            throw NullPointerException("Login is empty")

        screen_profile_toolbar.title = login + "'s profile"
        screen_profile_swiperefresh_layout.isEnabled = false

        val avatarParams = screen_profile_avatar.layoutParams as CoordinatorLayout.LayoutParams
        avatarParams.topMargin += resources.getDimension(R.dimen.avatar_margin_top).toInt()
        screen_profile_avatar.layoutParams = avatarParams

        val exitButtonParams = screen_profile_exit.layoutParams as CoordinatorLayout.LayoutParams
        exitButtonParams.behavior = null
        screen_profile_exit.layoutParams = exitButtonParams
        screen_profile_exit.visibility = View.GONE

        screen_profile_repos_container.adapter = adapter
        screen_profile_repos_container.layoutManager = LinearLayoutManager(context)
        screen_profile_repos_container.addItemDecoration(ItemDecorator(context, false))
        ViewCompat.setNestedScrollingEnabled(screen_profile_repos_container, false)
        adapter?.manager?.addDelegate(RepoProfileDelegate(activity, {}))

        setOnClickListeners()
        screen_profile_progress_bar.visibility = View.VISIBLE
        presenter?.getUserByLogin(login)
        presenter?.getReposByLogin(login, ConstValues.SortValues.BY_CREATION)

    }

    fun fillProfile(user: User?) {

        Glide.with(context)
                .load(user?.avatarUrl)
                .placeholder(R.drawable.base_avatar)
                .dontAnimate()
                .into(screen_profile_avatar)

        if(user?.name == null)
            screen_profile_field_name.visibility = View.GONE
        else {
            screen_profile_field_name.visibility = View.VISIBLE
            screen_profile_name.text = user.name
        }

        screen_profile_login.text = user?.login

        if(user?.bio == null)
            screen_profile_field_bio.visibility = View.GONE
        else {
            screen_profile_field_bio.visibility = View.VISIBLE
            screen_profile_bio.text = user.bio
        }

        if(user?.company == null)
            screen_profile_field_company.visibility = View.GONE
        else {
            screen_profile_field_company.visibility = View.VISIBLE
            screen_profile_company.text = user.company.toString()
        }

        if(user?.location == null)
            screen_profile_field_location.visibility = View.GONE
        else {
            screen_profile_field_location.visibility = View.VISIBLE
            screen_profile_location.text = user.location
        }

        if(user?.email == null)
            screen_profile_field_email.visibility = View.GONE
        else {
            screen_profile_field_email.visibility = View.VISIBLE
            screen_profile_email.text = user.email.toString()
        }
    }

    fun setOnClickListeners() {
        screen_profile_repos.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.REPOS_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }
        screen_profile_starred.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.STARRED_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }
        screen_profile_followers.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.FOLLOWERS_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }
        screen_profile_following.setOnClickListener{
            activity.supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.main_activity_container, FragmentListAny.newInstance(ConstValues.FragmentsData.FOLLOWING_KEY, login))
                    .addToBackStack(null)
                    .commit()
        }

    }

}