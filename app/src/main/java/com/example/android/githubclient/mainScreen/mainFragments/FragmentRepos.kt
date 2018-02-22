package com.example.android.githubclient.mainScreen.mainFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R

/**
 * Created by admin on 21.02.2018.
 */
class FragmentRepos : Fragment() {

    companion object {
        private val TAG = "TAG_FRAGMENT_REPOS"

        fun newInstance(): Fragment {
            return FragmentUsers()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_screen_repos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}