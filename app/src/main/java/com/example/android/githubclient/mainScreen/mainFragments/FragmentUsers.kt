package com.example.android.githubclient.mainScreen.mainFragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.presentation.model.User
import com.example.android.githubclient.base.presentation.presenter.UserListPresenter
import com.example.android.githubclient.base.presentation.view.UserListView
import com.example.android.githubclient.mainScreen.adapterDelegates.UserDelegate
import com.example.android.githubclient.mainScreen.decorators.UserItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_users.*


/**
 * Created by admin on 20.02.2018.
 */
class FragmentUsers : Fragment(), UserListView<UserListPresenter> {
    override var presenter: UserListPresenter? = null
    private var adapter: DelegationAdapter<Any>? = null

    interface firstFragmentCreated{
        fun hideBarCallback()
    }
    var hideSideBarCallback: firstFragmentCreated? = null

    override fun showError(error: String) {
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

    override fun showUsers(users: List<User>) {
        try {
            adapter?.addAllItems(users as ArrayList<Any>)
            hideSideBarCallback?.hideBarCallback()
        } catch (e: ClassCastException) {
            Log.e("showUsers", e.message)
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
            hideSideBarCallback = context as firstFragmentCreated
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

        screen_users.layoutManager = LinearLayoutManager(context)
        screen_users.adapter = adapter
        /*//var decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        //decoration.setDrawable(ContextCompat.getDrawable(activity, R.drawable.item_user_decoration))
        screen_users.addItemDecoration(UserItemDecorator(context, R.drawable.item_user_decoration), DividerItemDecoration.VERTICAL)*/
        adapter?.manager?.addDelegate(UserDelegate(activity, {}))
        presenter?.getUsers()
    }

}