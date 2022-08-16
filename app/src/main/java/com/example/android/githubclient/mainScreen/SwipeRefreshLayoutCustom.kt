package com.example.android.githubclient.mainScreen

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_screen_profile.view.*


/**
 * Created by admin on 04.03.2018.
 */
class SwipeRefreshLayoutCustom(context: Context, attributes: AttributeSet) :
        SwipeRefreshLayout(context, attributes) {

    override fun canChildScrollUp(): Boolean {
        return screen_profile_nestedscroll.scrollY != 0
    }
}