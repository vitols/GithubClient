package com.example.android.githubclient.otherScreens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues

/**
 * Created by admin on 21.03.2018.
 */
class FragmentRepositoryFiles : Fragment() {

    var login: String = ""
    var repoName: String = ""

    companion object {
        val TAG = "TAG_FRAGMENT_REPOSITORY_FILES"
        fun newInstance(login: String, repoName: String): Fragment {
            return FragmentRepositoryFiles().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.LOGIN_KEY, login)
                    putString(ConstValues.FragmentsData.REPO_NAME_KEY, repoName)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login = arguments?.getString(ConstValues.FragmentsData.LOGIN_KEY).toString()
        repoName = arguments?.getString(ConstValues.FragmentsData.REPO_NAME_KEY).toString()
    }
}