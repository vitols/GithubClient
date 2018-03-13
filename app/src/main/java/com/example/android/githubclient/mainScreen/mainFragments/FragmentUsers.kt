package com.example.android.githubclient.mainScreen.mainFragments

import android.animation.LayoutTransition
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import com.example.android.githubclient.R
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserListPresenter
import com.example.android.githubclient.base.presentation.view.UserListView
import com.example.android.githubclient.mainScreen.adapterDelegates.UserDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_users.*
import android.view.MenuInflater
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_screen_profile.*
import kotlinx.android.synthetic.main.fragment_screen_repos.*


/**
 * Created by admin on 20.02.2018.
 */
class FragmentUsers : Fragment(),
        UserListView<UserListPresenter>,
        SwipeRefreshLayout.OnRefreshListener {

    override var presenter: UserListPresenter? = null
    private var adapter: DelegationAdapter<Any>? = null

    interface FragmentListCallbackInterface {
        fun hideBarCallback()
        fun openScreenMe()
        fun openProfileScreenByLogin(login: String)
    }
    var mainActivityCallback: FragmentListCallbackInterface? = null

    override fun showError(error: String) {
        screen_users_swiperefresh.isRefreshing = false
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(error)
                .setTitle("Error occured")
                .setPositiveButton("OK",
                        DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                        })

        val dialog = builder.create()
        dialog.show()
    }


    override fun onRefresh() {
        presenter?.getUsers()

    }

    override fun showUsers(users: List<User>) {
        if(users.isEmpty()) {
            adapter?.clearAllItems()
            screen_users_swiperefresh.isRefreshing = false
            Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            adapter?.replaceAllItems(users as ArrayList<Any>)
            screen_users_swiperefresh.isRefreshing = false
            mainActivityCallback?.hideBarCallback()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    companion object {
        private val TAG = "TAG_FRAGMENT_USERS"

        fun newInstance(): Fragment {
            return FragmentUsers()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mainActivityCallback = context as FragmentListCallbackInterface
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

        screen_users.layoutManager = LinearLayoutManager(context)
        screen_users.adapter = adapter
        screen_users.addItemDecoration(ItemDecorator(context))

        adapter?.manager?.addDelegate(
                UserDelegate(activity, {
                    itemView, login ->
                    YoYo.with(Techniques.ZoomIn)
                            .duration(50)
                            .playOn(itemView);
                        if (login == LoginController.instance.user?.login && LoginController.instance.isLoggedIn())
                            mainActivityCallback?.openScreenMe()
                        else
                            mainActivityCallback?.openProfileScreenByLogin(login)

        }))

        screen_users_swiperefresh.isRefreshing = true
        presenter?.getUsers()
    }


    fun setToolbarItems() {
        screen_users_toolbar.inflateMenu(R.menu.menu_toolbar)
        var item = screen_users_toolbar
                .menu
                .getItem(0)

        var searchView = item.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        val insetLeft = screen_users_toolbar.contentInsetLeft
        val insetRight = screen_users_toolbar.contentInsetRight

        searchView.setOnSearchClickListener { screen_users_toolbar.setContentInsetsAbsolute(0, 0)}
        searchView.setOnCloseListener(object: SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                screen_users_toolbar.setContentInsetsAbsolute(insetLeft, insetRight)
                searchView.setQuery("", false)
                searchView.onActionViewCollapsed()
                return true
            }

        })

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

    fun navigateToFragment(fragment: Fragment, login: String) {


    }
}
