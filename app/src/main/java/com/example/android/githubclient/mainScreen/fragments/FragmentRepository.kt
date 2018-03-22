package com.example.android.githubclient.mainScreen.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_screen_repository, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        login = arguments.getString(ConstValues.FragmentsData.LOGIN_KEY)
        repoName = arguments.getString(ConstValues.FragmentsData.REPO_NAME_KEY)
        screen_repository_toolbar.title = repoName
        repoName = getShortName(repoName)

        val viewPagerAdapter = ViewPagerAdapter(activity.supportFragmentManager)
        viewPagerAdapter.addFragment(FragmentRepositoryInfo.newInstance(login, repoName), "INFO")
        viewPagerAdapter.addFragment(FragmentRepositoryFiles.newInstance(login, repoName), "FILES")
        screen_repository_viewpager.adapter = viewPagerAdapter
        screen_repository_tabs.setupWithViewPager(screen_repository_viewpager)
        screen_repository_toolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    private fun getShortName(fullName: String): String {
        if(fullName.contains("/")) {
            return fullName.split("/")[1]
        }
        return fullName
    }
}