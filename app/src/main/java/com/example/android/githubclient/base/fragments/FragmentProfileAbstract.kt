package com.example.android.githubclient.base.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserPresenter
import com.example.android.githubclient.base.presentation.view.UserView
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import com.example.android.githubclient.otherScreens.FragmentListAny
import kotlinx.android.synthetic.main.fragment_screen_profile.*

/**
 * Created by admin on 21.03.2018.
 */
abstract class FragmentProfileAbstract : Fragment(), UserView<UserPresenter> {
    override var presenter: UserPresenter? = null
    var adapter: DelegationAdapter<Any>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = UserPresenter(this)
        adapter = DelegationAdapter<Any>()
        return inflater.inflate(R.layout.fragment_screen_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val avatarParams = screen_profile_avatar.layoutParams as CoordinatorLayout.LayoutParams
        avatarParams.topMargin += resources.getDimension(R.dimen.avatar_margin_top).toInt()
        screen_profile_avatar.layoutParams = avatarParams

        ViewCompat.setNestedScrollingEnabled(screen_profile_repos_container, false)

        screen_profile_repos_container.adapter = adapter
        screen_profile_repos_container.layoutManager = LinearLayoutManager(context)
        screen_profile_repos_container.addItemDecoration(ItemDecorator(requireActivity(), LinearLayoutManager.VERTICAL))

    }

    override fun showError(error: String) {
        AlertDialog.Builder(requireContext())
                .setMessage(error)
                .setTitle(ConstValues.ErrorDialog.TITLE)
                .setPositiveButton(ConstValues.ErrorDialog.OK, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
        screen_profile_swiperefresh_layout?.isRefreshing = false
    }

    override fun showRepos(listOfRepos: List<Repo>, login: String) {
        if(screen_profile_progress_bar != null)
            screen_profile_progress_bar.visibility = View.GONE
        else
            return

        if(listOfRepos.isEmpty()) {
            screen_profile_empty_repos.text = login + screen_profile_empty_repos.text
            screen_profile_empty_repos.visibility = View.VISIBLE
        } else {
            try {
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
    }

    fun fillProfile(user: User?) {
        Glide.with(context)
                .load(user?.avatarUrl)
                .placeholder(R.drawable.base_avatar)
                .dontAnimate()
                .into(screen_profile_avatar)

        if(user?.name == null) {
            screen_profile_field_name.visibility = View.GONE
        }
        else {
            screen_profile_field_name.visibility = View.VISIBLE
            screen_profile_name.text = user.name
        }

        screen_profile_login.setText(user?.login)

        if(user?.bio == null) {
            screen_profile_field_bio.visibility = View.GONE
        }
        else {
            screen_profile_field_bio.visibility = View.VISIBLE
            screen_profile_bio.text = user.bio
        }

        if(user?.company == null) {
            screen_profile_field_company.visibility = View.GONE
        }
        else {
            screen_profile_field_company.visibility = View.VISIBLE
            screen_profile_company.text = user.company.toString()
        }

        if(user?.location == null) {
            screen_profile_field_location.visibility = View.GONE
        }
        else {
            screen_profile_field_location.visibility = View.VISIBLE
            screen_profile_location.text = user.location
        }

        if(user?.email == null) {
            screen_profile_field_email.visibility = View.GONE
        }
        else {
            screen_profile_field_email.visibility = View.VISIBLE
            screen_profile_email.text = user.email.toString()
        }
    }

    open fun setToolbarItems() {
        if(arguments?.getBoolean(ConstValues.FragmentsData.ADD_BACK_NAVIGATION_KEY) == false)
            screen_profile_toolbar.navigationIcon = null
        else
            screen_profile_toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    open fun setOnClickListeners(login: String) {
        screen_profile_repos.setOnClickListener{
            YoYo.with(Techniques.BounceIn)
                    .duration(50)
                    .playOn(it)
            makeTransaction(ConstValues.FragmentsData.REPOS_KEY, login)
        }
        screen_profile_starred.setOnClickListener{
            YoYo.with(Techniques.BounceIn)
                    .duration(50)
                    .playOn(it)
            makeTransaction(ConstValues.FragmentsData.STARRED_KEY, login)
        }
        screen_profile_followers.setOnClickListener{
            YoYo.with(Techniques.BounceIn)
                    .duration(50)
                    .playOn(it)
            makeTransaction(ConstValues.FragmentsData.FOLLOWERS_KEY, login)
        }
        screen_profile_following.setOnClickListener{
            YoYo.with(Techniques.BounceIn)
                    .duration(50)
                    .playOn(it)
            makeTransaction(ConstValues.FragmentsData.FOLLOWING_KEY, login)
        }

    }
    private fun makeTransaction(flag: String, login: String) {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.main_activity_container, FragmentListAny.newInstance(flag, login))
                .addToBackStack(null)
                .commit()
    }
}