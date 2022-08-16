package com.example.android.githubclient.otherScreens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.example.android.githubclient.mainScreen.adapters.RepoDelegate
import com.example.android.githubclient.mainScreen.adapters.UserDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import com.example.android.githubclient.mainScreen.fragments.FragmentProfileAuthorized
import com.example.android.githubclient.mainScreen.fragments.FragmentRepository
import com.example.android.githubclient.mainScreen.fragments.FragmentUsers
import kotlinx.android.synthetic.main.fragment_screen_repos.*

/**
 * Created by admin on 10.03.2018.
 */
class FragmentListAny : Fragment(), AnyListView<AnyListPresenter> {

    override var presenter: AnyListPresenter? = null
    var adapter: DelegationAdapter<Any>? = null

    var data: String = ""
    var login: String = ""
    var mainActivityCallback: FragmentUsers.FragmentListListener? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mainActivityCallback = context as FragmentUsers.FragmentListListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement FragmentUsers.FragmentListListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = DelegationAdapter()
        presenter = AnyListPresenter(this)
        return inflater?.inflate(R.layout.fragment_screen_repos, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = arguments?.get(ConstValues.FragmentsData.DATA).toString()
        login = arguments?.get(ConstValues.FragmentsData.LOGIN_KEY).toString()

        if(login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
            screen_repos_toolbar.title = "My " + data.toLowerCase()
        else
            screen_repos_toolbar.title = login + "'s " + data.toLowerCase()
        screen_repos_toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        screen_repos_container.layoutManager = LinearLayoutManager(context)
        screen_repos_container.adapter = adapter
        screen_repos_container.addItemDecoration(ItemDecorator(requireActivity(), LinearLayoutManager.VERTICAL))

        adapter?.manager?.addDelegate(UserDelegate(activity
        ) { itemView: View, login: String ->
            YoYo.with(Techniques.ZoomIn)
                    .duration(100)
                    .playOn(itemView);
            if (login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
                openScreenProfile(FragmentProfileAuthorized.newInstance())
            else
                openScreenProfile(FragmentProfileNotAuthorized.newInstance(login))
        })

        screen_repos_progress_bar.visibility = View.VISIBLE
        when(data) {
            ConstValues.FragmentsData.REPOS_KEY -> {
                adapter?.manager?.addDelegate(RepoDelegate(activity) { _, _, name ->
                    requireActivity().supportFragmentManager
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .add(R.id.main_activity_container, FragmentRepository.newInstance(login, name))
                            .addToBackStack(FragmentRepository.TAG)
                            .commit()
                })
                presenter?.getListRepos(login)
            }
            ConstValues.FragmentsData.STARRED_KEY -> {
                adapter?.manager?.addDelegate(RepoDelegate(activity) { _, owner, name ->
                    requireActivity().supportFragmentManager
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .add(R.id.main_activity_container, FragmentRepository.newInstance(owner, name))
                            .addToBackStack(FragmentRepository.TAG)
                            .commit()
                })
                presenter?.getListStarred(login)
            }
            ConstValues.FragmentsData.FOLLOWERS_KEY -> presenter?.getListFollowers(login)
            ConstValues.FragmentsData.FOLLOWING_KEY -> presenter?.getListFollowing(login)
        }
    }

    override fun showListRepos(repos: List<Repo>) {
        showRepos(repos, ConstValues.EmptyList.NO_REPOS_ME, ConstValues.EmptyList.NO_REPOS_ANOTHER)
    }
    override fun showListStarred(repos: List<Repo>) {
        showRepos(repos, ConstValues.EmptyList.NO_STARRED_ME, ConstValues.EmptyList.NO_STARRED_ANOTHER)
    }
    override fun showListFollowers(users: List<User>) {
        showUsers(users, ConstValues.EmptyList.NO_FOLLOWERS_ME, ConstValues.EmptyList.NO_FOLLOWERS_ANOTHER)
    }
    override fun showListFollowing(users: List<User>) {
        showUsers(users, ConstValues.EmptyList.NO_FOLLOWING_ME, ConstValues.EmptyList.NO_FOLLOWING_ANOTHER)
    }

    override fun showError(error: String) {
        AlertDialog.Builder(requireContext())
                .setMessage(error)
                .setTitle(ConstValues.ErrorDialog.TITLE)
                .setPositiveButton(ConstValues.ErrorDialog.OK, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
    }

    private fun showRepos(repos: List<Repo>, emptyViewTextForMe: String, emptyViewTextForAnother: String) {
        if(screen_repos_progress_bar == null)
            return
        screen_repos_progress_bar.visibility = View.GONE
        if(repos.isEmpty())
        {
            if(login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
                screen_repos_empty_view.text = emptyViewTextForMe
            else
                screen_repos_empty_view.text = login + emptyViewTextForAnother

            screen_repos_empty_view.visibility = View.VISIBLE
            adapter?.clearAllItems()
            return
        }

        screen_repos_container.visibility = View.VISIBLE
        try {
            adapter?.replaceAllItems(repos as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    private fun showUsers(users: List<User>, emptyViewTextForMe: String, emptyViewTextForAnother: String) {
        if(screen_repos_progress_bar == null)
            return
        screen_repos_progress_bar.visibility = View.GONE
        if(users.isEmpty())
        {
            if(login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
                screen_repos_empty_view.text = emptyViewTextForMe
            else
                screen_repos_empty_view.text = login + emptyViewTextForAnother
            screen_repos_empty_view.visibility = View.VISIBLE
            adapter?.clearAllItems()
            return
        }
        screen_repos_empty_view.visibility = View.GONE
        try {
            adapter?.replaceAllItems(users as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    private fun openScreenProfile(fragment: Fragment) {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.main_activity_container, fragment)
                .addToBackStack(null)
                .commit()
    }

}