package com.example.android.githubclient.mainScreen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.adapters.DelegationAdapter
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.presenter.RepoListPresenter
import com.example.android.githubclient.base.presentation.view.RepoListView
import com.example.android.githubclient.mainScreen.adapters.RepoDelegate
import com.example.android.githubclient.mainScreen.decorators.ItemDecorator
import kotlinx.android.synthetic.main.fragment_screen_repos.*

/**
 * Created by admin on 21.02.2018.
 */
class FragmentRepos : Fragment(), RepoListView<RepoListPresenter> {

    override var presenter: RepoListPresenter? = null
    var adapter: DelegationAdapter<Any>? = null

    companion object {
        private val TAG = "TAG_FRAGMENT_REPOS"

        fun newInstance(addNavigationButton: Any?): Fragment {
            return FragmentRepos().apply {
                arguments = Bundle().apply {
                    putBoolean(ConstValues.FragmentsData.ADD_BACK_NAVIGATION_KEY,
                            if(addNavigationButton != null) addNavigationButton as Boolean else true)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = RepoListPresenter(this)
        adapter = DelegationAdapter()
        return inflater.inflate(R.layout.fragment_screen_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarItems()
        screen_repos_container.layoutManager = LinearLayoutManager(context)
        screen_repos_container.adapter = adapter
        screen_repos_container.addItemDecoration(ItemDecorator(requireActivity(), R.dimen.item_repo_divider_left_margin, R.dimen.item_repo_divider_left_margin))

        adapter?.manager?.addDelegate(RepoDelegate(activity) { v, owner, name ->
            /*YoYo.with(Techniques.BounceIn)
                    .playOn(v)*/
            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.main_activity_container, FragmentRepository.newInstance(owner, name))
                    .addToBackStack(FragmentRepository.TAG)
                    .commit()
        })

        screen_repos_container.visibility = View.GONE
        screen_repos_progress_bar.visibility = View.VISIBLE
        presenter?.getRepos()
    }

    override fun showError(error: String) {
        AlertDialog.Builder(requireContext())
                .setMessage(error)
                .setTitle(ConstValues.ErrorDialog.TITLE)
                .setPositiveButton(ConstValues.ErrorDialog.OK, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
        screen_repos_progress_bar.visibility = View.GONE
    }

    override fun showRepos(repos: List<Repo>) {
        screen_repos_progress_bar.visibility = View.GONE
        if (repos.isEmpty()) {
            Toast.makeText(context, "No users found", Toast.LENGTH_SHORT).show()
            adapter?.clearAllItems()
            return
        } else {
            try {
                screen_repos_container.visibility = View.VISIBLE
                adapter?.replaceAllItems(repos as ArrayList<Any>)
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
        }
    }

    private fun setToolbarItems() {
        screen_repos_toolbar.inflateMenu(R.menu.menu_toolbar)
        val searchView = screen_repos_toolbar
                .menu
                .getItem(0)
                .actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        val insetLeft = screen_repos_toolbar.contentInsetLeft
        val insetRight = screen_repos_toolbar.contentInsetRight

        searchView.setOnSearchClickListener { screen_repos_toolbar.setContentInsetsAbsolute(0, 0)}
        searchView.setOnCloseListener {
            screen_repos_toolbar.setContentInsetsAbsolute(insetLeft, insetRight)
            searchView.setQuery("", false)
            searchView.onActionViewCollapsed()
            true
        }
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

        if(arguments?.getBoolean(ConstValues.FragmentsData.ADD_BACK_NAVIGATION_KEY) == false)
            screen_repos_toolbar.navigationIcon = null
        else
            screen_repos_toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

}