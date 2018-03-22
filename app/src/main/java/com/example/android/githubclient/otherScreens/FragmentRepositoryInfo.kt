package com.example.android.githubclient.otherScreens

import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.base.presentation.model.Repo
import com.example.android.githubclient.base.presentation.presenter.RepoPresenter
import com.example.android.githubclient.base.presentation.view.RepoView
import kotlinx.android.synthetic.main.fragment_repository_info.*

/**
 * Created by admin on 21.03.2018.
 */
class FragmentRepositoryInfo : Fragment(), RepoView<RepoPresenter> {

    override var presenter: RepoPresenter? = null
    var login: String = ""
    var repoName: String = ""

    companion object {
        val TAG = "TAG_FRAGMENT_REPOSITORY_INFO"
        fun newInstance(login: String, repoName: String): Fragment {
            return FragmentRepositoryInfo().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.FragmentsData.LOGIN_KEY, login)
                    putString(ConstValues.FragmentsData.REPO_NAME_KEY, repoName)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = RepoPresenter(this)
        return inflater!!.inflate(R.layout.fragment_repository_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        screen_repository_info_progress_bar.visibility = View.VISIBLE
        screen_repository_info_markdown_progress_bar.visibility = View.VISIBLE
        login = arguments.getString(ConstValues.FragmentsData.LOGIN_KEY)
        repoName = arguments.getString(ConstValues.FragmentsData.REPO_NAME_KEY)
        presenter?.getRepo(login, repoName)
        presenter?.getFileContent(login, repoName)
    }

    override fun showRepo(repo: Repo) {
        if (screen_repository_info_progress_bar == null)
            return
        screen_repository_info_progress_bar.visibility = View.GONE
        fillRepo(repo)
    }

    override fun showReadme(readme: String?) {
        if(screen_repository_info_markdown_progress_bar == null)
            return
        screen_repository_info_markdown_progress_bar.visibility = View.GONE
        if(readme != null) {
            screen_repository_info_markdown.loadMarkdown(readme)
        }
        else {
            screen_repository_info_readme_placeholder.visibility = View.VISIBLE
        }

    }

    override fun showError(error: String) {
        AlertDialog.Builder(context)
                .setMessage(error)
                .setTitle(ConstValues.ErrorDialog.TITLE)
                .setPositiveButton(ConstValues.ErrorDialog.OK, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
    }

    private fun fillRepo(repo: Repo) {

        screen_repository_info_field_name.visibility = View.VISIBLE
        screen_repository_info_name.movementMethod = LinkMovementMethod.getInstance()
        screen_repository_info_name.text = Html.fromHtml("<a href='" + repo.htmlUrl + "'>" + repo.name + "</a>")

        if(repo.description != null) {
            screen_repository_info_field_description.visibility = View.VISIBLE
            screen_repository_info_description.text = repo.description.toString()
        } else
            screen_repository_info_field_description.visibility = View.GONE

        screen_repository_info_field_language.visibility = View.VISIBLE
        if(repo.language != null) {
            screen_repository_info_field_language.visibility = View.VISIBLE
            screen_repository_info_language.text = repo.language
        } else
            screen_repository_info_field_language.visibility = View.GONE

        screen_repository_info_watchers.text = repo.watchersCount.toString()
        screen_repository_info_stargazers.text = repo.stargazersCount.toString()
        screen_repository_info_issue.text = repo.openIssuesCount.toString()
        screen_repository_info_forks.text = repo.forksCount.toString()
    }
}