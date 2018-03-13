package com.example.android.githubclient.mainScreen.mainFragments

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.presenter.RepoListPresenter
import com.example.android.githubclient.base.presentation.view.RepoListView
import com.example.android.githubclient.mainScreen.adapterDelegates.RepoDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_repos.*
import kotlinx.android.synthetic.main.fragment_screen_users.*

/**
 * Created by admin on 21.02.2018.
 */
class FragmentRepos : Fragment(), RepoListView<RepoListPresenter> {

    override var presenter: RepoListPresenter? = null
    var adapter: DelegationAdapter<Any>? = null

    companion object {
        private val TAG = "TAG_FRAGMENT_REPOS"

        fun newInstance(): Fragment {
            return FragmentRepos()
        }
    }


    override fun showError(error: String) {
        screen_repos_progress_bar.visibility = View.GONE
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(error)
                .setTitle(ConstValues.Errors.TITLE)
                .setPositiveButton(ConstValues.Errors.OK,
                        DialogInterface.OnClickListener {
                            dialog, _ -> dialog.cancel()
                        })

        val dialog = builder.create()
        dialog.show()
    }

    override fun showRepos(repos: List<Repo>) {
        screen_repos_progress_bar.visibility = View.GONE
        if(repos.isEmpty())
        {
            Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
            adapter?.clearAllItems()
            return
        }
        try {
            screen_repos.visibility = View.VISIBLE
            adapter?.replaceAllItems(repos as ArrayList<Any>)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = RepoListPresenter(this)
        adapter = DelegationAdapter()
        return inflater!!.inflate(R.layout.fragment_screen_repos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarItems()
        screen_repos.layoutManager = LinearLayoutManager(context)
        screen_repos.adapter = adapter
        screen_repos.addItemDecoration(ItemDecorator(activity))

        adapter?.manager?.addDelegate(RepoDelegate(activity, {
            YoYo.with(Techniques.BounceIn)
                    .duration(200)
                    .playOn(it)
        }))

        screen_repos.visibility = View.GONE
        screen_repos_progress_bar.visibility = View.VISIBLE
        presenter?.getRepos()
    }

    fun setToolbarItems() {
        screen_repos_toolbar.inflateMenu(R.menu.menu_toolbar)

        var item = screen_repos_toolbar
                .menu
                .getItem(0)
        var searchView = item.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        val insetLeft = screen_repos_toolbar.contentInsetLeft
        val insetRight = screen_repos_toolbar.contentInsetRight

        searchView.setOnSearchClickListener { screen_repos_toolbar.setContentInsetsAbsolute(0, 0)}
        searchView.setOnCloseListener(object: SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                screen_repos_toolbar.setContentInsetsAbsolute(insetLeft, insetRight)
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
                        screen_repos_progress_bar.visibility = View.VISIBLE
                        presenter?.getRepos()
                        return true
                    }

                    screen_repos_progress_bar.visibility = View.VISIBLE
                    presenter?.searchRepos(newText)
                    return true
                }
                return false
            }

        })
    }

}