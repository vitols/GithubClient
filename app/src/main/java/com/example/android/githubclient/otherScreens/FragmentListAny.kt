package com.example.android.githubclient.otherScreens

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.AnyListPresenter
import com.example.android.githubclient.base.presentation.view.AnyListView
import com.example.android.githubclient.mainScreen.adapterDelegates.RepoDelegate
import com.example.android.githubclient.mainScreen.adapterDelegates.UserDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import com.example.android.githubclient.mainScreen.mainFragments.FragmentUsers
import kotlinx.android.synthetic.main.fragment_screen_repos.*

/**
 * Created by admin on 10.03.2018.
 */
class FragmentListAny : Fragment(), AnyListView<AnyListPresenter> {
    override var presenter: AnyListPresenter? = null
    var adapter: DelegationAdapter<Any>? = null

    var data: String = ""
    var login: String = ""
    var mainActivityCallback: FragmentUsers.FragmentListCallbackInterface? = null

    companion object {
        private val TAG = "FRAGMENT_LIST_ANY"
        fun newInstance(data: String, login: String): Fragment {
            return FragmentListAny().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.DATA, data)
                    putString(ConstValues.FragmentsData.LOGIN_KEY, login)
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mainActivityCallback = context as FragmentUsers.FragmentListCallbackInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement Profile callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = DelegationAdapter()
        presenter = AnyListPresenter(this)
        return inflater?.inflate(R.layout.fragment_screen_repos, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = arguments.get(ConstValues.FragmentsData.DATA).toString()
        login = arguments.get(ConstValues.FragmentsData.LOGIN_KEY).toString()

        screen_repos_toolbar.title = login + "'s " + data.toLowerCase()

        screen_repos.layoutManager = LinearLayoutManager(context)
        screen_repos.adapter = adapter
        screen_repos.addItemDecoration(ItemDecorator(activity))

        adapter?.manager?.addDelegate(UserDelegate(activity,  {
            itemView: View, login: String ->
            YoYo.with(Techniques.ZoomIn)
                    .duration(100)
                    .playOn(itemView);
                if (login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
                    mainActivityCallback?.openScreenMe()
                else
                    openScreenProfileAnother(login)
        }
        ))

        adapter?.manager?.addDelegate(RepoDelegate(activity, {}))

        screen_repos_progress_bar.visibility = View.VISIBLE

        Log.e("data is ", data + "!")

        when(data) {
            ConstValues.FragmentsData.REPOS_KEY -> presenter?.getListRepos(login)
            ConstValues.FragmentsData.STARRED_KEY -> presenter?.getListStarred(login)
            ConstValues.FragmentsData.FOLLOWERS_KEY -> presenter?.getListFollowers(login)
            ConstValues.FragmentsData.FOLLOWING_KEY -> presenter?.getListFollowing(login)
        }
    }

    override fun showListRepos(repos: List<Repo>) {
        screen_repos_progress_bar.visibility = View.GONE
        if(repos.isEmpty())
        {
            Log.e("showListRepos", "isEmpty")
            Toast.makeText(context, "No repos found", Toast.LENGTH_SHORT).show()
            adapter?.clearAllItems()
            return
        }
        Log.e("showListRepos", "notIsEmpty")
        try {
            adapter?.replaceAllItems(repos as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun showListStarred(starred: List<Repo>) {
        screen_repos_progress_bar.visibility = View.GONE
        if(starred.isEmpty())
        {
            Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
            adapter?.clearAllItems()
            return
        }
        try {
            adapter?.replaceAllItems(starred as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun showListFollowers(followers: List<User>) {
        screen_repos_progress_bar.visibility = View.GONE
        if(followers.isEmpty())
        {
            Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
            adapter?.clearAllItems()
            return
        }
        try {
            adapter?.replaceAllItems(followers as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun showListFollowing(following: List<User>) {
        screen_repos_progress_bar.visibility = View.GONE
        if(following.isEmpty())
        {
            Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
            adapter?.clearAllItems()
            return
        }
        try {
            adapter?.replaceAllItems(following as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun showError(error: String) {

    }

    private fun openScreenProfileAnother(login: String) {
        activity.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.main_activity_container, FragmentProfileUnauthorized.newInstance(login))
                .addToBackStack(null)
                .commit()
    }

}