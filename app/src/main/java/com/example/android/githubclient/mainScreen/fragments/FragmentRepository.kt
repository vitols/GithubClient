package com.example.android.githubclient.mainScreen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.mainScreen.adapters.ViewPagerAdapter
import com.example.android.githubclient.otherScreens.FragmentRepositoryFiles
import com.example.android.githubclient.otherScreens.FragmentRepositoryInfo
import kotlinx.android.synthetic.main.fragment_screen_repository.*

/**
 * Created by admin on 21.03.2018.
 */
class FragmentRepository : Fragment() {

    var login: String = ""
    var repoName: String = ""


    companion object {
        val TAG = "TAG_FRAGMENT_REPOSITORY"
        fun newInstance(login: String, repoName: String): Fragment {
            return FragmentRepository().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.LOGIN_KEY, login)
                    putString(ConstValues.FragmentsData.REPO_NAME_KEY, repoName)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_screen_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login = arguments?.getString(ConstValues.FragmentsData.LOGIN_KEY).toString()
        repoName = arguments?.getString(ConstValues.FragmentsData.REPO_NAME_KEY).toString()
        screen_repository_toolbar.title = repoName
        repoName = getShortName(repoName)

        val viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragment(FragmentRepositoryInfo.newInstance(login, repoName), "INFO")
        viewPagerAdapter.addFragment(FragmentRepositoryFiles.newInstance(login, repoName), "FILES")
        screen_repository_viewpager.adapter = viewPagerAdapter
        screen_repository_tabs.setupWithViewPager(screen_repository_viewpager)
        setToolbarItems()
    }

    private fun getShortName(fullName: String): String {
        if(fullName.contains("/")) {
            return fullName.split("/")[1]
        }
        return fullName
    }

    private fun setToolbarItems() {
        screen_repository_toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        screen_repository_toolbar.inflateMenu(R.menu.menu_show_graph)

        val item = screen_repository_toolbar
                .menu
                .getItem(0)
        item.setOnMenuItemClickListener {
            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    //.add(R.id.main_activity_container, FragmentGraph.newInstance(""), FragmentGraph.TAG)
                    .addToBackStack(null)
                    .commit();
            true}
    }
}