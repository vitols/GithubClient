package com.example.android.githubclient.otherScreens

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.githubclient.R
import com.example.android.githubclient.base.ConstValues
import com.example.android.githubclient.mainScreen.fragments.FragmentProfileAuthorized
import kotlinx.android.synthetic.main.fragment_screen_edit_profile.*


/**
 * Created by admin on 20.03.2018.
 */
class FragmentEditProfile : Fragment() {

    interface EditProfileApplyChangesListener {
        fun onApplyChanges(name: String, bio: String, company: String, location: String, email: String, blog: String, hireable: Boolean)
    }

    var applyChangesLisener: EditProfileApplyChangesListener? = null

    companion object {
        val TAG = "TAG_FRAGMENT_EDIT_PROFILE"

        fun newInstance(name: String, bio: String, company: String, location: String, email: String,
                        blog: String, hireable: Boolean): Fragment {

            return FragmentEditProfile().apply {
                arguments = Bundle().apply {
                    putString(ConstValues.UserData.NAME, name)
                    putString(ConstValues.UserData.BIO, bio)
                    putString(ConstValues.UserData.COMPANY, company)
                    putString(ConstValues.UserData.LOCATION, location)
                    putString(ConstValues.UserData.EMAIL, email)
                    putString(ConstValues.UserData.BLOG, blog)
                    putBoolean(ConstValues.UserData.HIREABLE, hireable)
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
             applyChangesLisener = activity
                     .supportFragmentManager
                     .findFragmentByTag("SCREEN_PROFILE") as EditProfileApplyChangesListener
        } catch (e: ClassCastException) {
            throw ClassCastException(FragmentProfileAuthorized.TAG + " must implement EditProfileApplyChangesListener callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_screen_edit_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        setToolbarItems()
        setData()
    }

    fun setToolbarItems() {
        screen_edit_profile_toolbar.inflateMenu(R.menu.menu_apply)

        var item = screen_edit_profile_toolbar
                .menu
                .getItem(0)
        item.setOnMenuItemClickListener {
            if (applyChangesLisener != null) {

                applyChangesLisener?.onApplyChanges(
                        screen_edit_profile_name.text.toString(),
                        screen_edit_profile_bio.text.toString(),
                        screen_edit_profile_company.text.toString(),
                        screen_edit_profile_location.text.toString(),
                        screen_edit_profile_email.text.toString(),
                        screen_edit_profile_blog.text.toString(),
                        false)
            }
            false
        }
        screen_edit_profile_toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    fun setData() {
        var name = arguments.getString(ConstValues.UserData.NAME)
        var bio = arguments.getString(ConstValues.UserData.BIO)
        var company = arguments.getString(ConstValues.UserData.COMPANY)
        var location = arguments.getString(ConstValues.UserData.LOCATION)
        var email = arguments.getString(ConstValues.UserData.EMAIL)
        var blog = arguments.getString(ConstValues.UserData.BLOG)

        screen_edit_profile_name.setText(name, TextView.BufferType.EDITABLE)
        screen_edit_profile_bio.setText(bio, TextView.BufferType.EDITABLE)
        screen_edit_profile_company.setText(company, TextView.BufferType.EDITABLE)
        screen_edit_profile_location.setText(location, TextView.BufferType.EDITABLE)
        screen_edit_profile_email.setText(email, TextView.BufferType.EDITABLE)
        screen_edit_profile_blog.setText(blog, TextView.BufferType.EDITABLE)
    }
}