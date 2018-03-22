package com.example.android.githubclient.mainScreen.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import com.example.android.githubclient.R
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserListPresenter
import com.example.android.githubclient.base.presentation.view.UserListView
import com.example.android.githubclient.mainScreen.adapters.UserDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_users.*
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.otherScreens.FragmentProfileNotAuthorized


/**
 * Created by admin on 20.02.2018.
 */
class FragmentUsers : Fragment(),
        UserListView<UserListPresenter>,
        SwipeRefreshLayout.OnRefreshListener {

    override var presenter: UserListPresenter? = null
    private var adapter: DelegationAdapter<Any>? = null

    interface FragmentListListener {
        fun hideBarCallback()
        fun openProfileScreenByLogin(fragment: Fragment)
    }
    var mainActivityCallback: FragmentListListener? = null

    companion object {
        private val TAG = "TAG_FRAGMENT_USERS"

        fun newInstance(): Fragment {
            return FragmentUsers()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mainActivityCallback = context as FragmentListListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = UserListPresenter(this)
        adapter = DelegationAdapter()
        return inflater!!.inflate(R.layout.fragment_screen_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarItems()

        screen_users_swiperefresh.setOnRefreshListener(this)

        screen_users_container.layoutManager = LinearLayoutManager(context)
        screen_users_container.adapter = adapter
        screen_users_container.addItemDecoration(ItemDecorator(context, LinearLayoutManager.VERTICAL, R.dimen.item_user_divider_left_margin))

        adapter?.manager?.addDelegate(
                UserDelegate(activity, {
                    itemView, login ->
                    YoYo.with(Techniques.BounceIn)
                            .duration(10)
                            .playOn(itemView);
                        if (login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
                            mainActivityCallback?.openProfileScreenByLogin(FragmentProfileAuthorized.newInstance())
                        else
                            mainActivityCallback?.openProfileScreenByLogin(FragmentProfileNotAuthorized.newInstance(login))

        }))

        screen_users_swiperefresh.isRefreshing = true
        presenter?.getUsers()
    }

    override fun showError(error: String) {
        AlertDialog.Builder(context)
                .setMessage(error)
                .setTitle(ConstValues.ErrorDialog.TITLE)
                .setPositiveButton(ConstValues.ErrorDialog.OK, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
        screen_users_swiperefresh.isRefreshing = false
    }

    override fun onRefresh() {
        presenter?.getUsers()

    }

    override fun showUsers(users: List<User>) {
        if(users.isEmpty()) {
            screen_users_swiperefresh.isRefreshing = false
            screen_users_empty_view.visibility = View.VISIBLE
            screen_users_empty_view.text = ConstValues.EmptyList.NO_USERS
            adapter?.clearAllItems()
            return
        }

        screen_users_empty_view.visibility = View.GONE
        try {
            adapter?.replaceAllItems(users as ArrayList<Any>)
            screen_users_swiperefresh.isRefreshing = false
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    fun setToolbarItems() {
        screen_users_toolbar.inflateMenu(R.menu.menu_toolbar)
        val searchView = screen_users_toolbar
                .menu
                .getItem(0)
                .actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        val insetLeft = screen_users_toolbar.contentInsetLeft
        val insetRight = screen_users_toolbar.contentInsetRight

        searchView.setOnSearchClickListener { screen_users_toolbar.setContentInsetsAbsolute(0, 0)}
        searchView.setOnCloseListener {
            screen_users_toolbar.setContentInsetsAbsolute(insetLeft, insetRight)
            searchView.setQuery("", false)
            searchView.onActionViewCollapsed()
            screen_users_empty_view.visibility = View.GONE
            true
        }

        searchView.setOnQueryTextListener( object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if(newText.isEmpty()) {
                        screen_users_swiperefresh.isRefreshing = true
                        presenter?.getUsers()
                        return true
                    }
                    screen_users_swiperefresh.isRefreshing = true
                    presenter?.searchUsers(newText)
                    return true
                }
                return false
            }

        })
    }
}
