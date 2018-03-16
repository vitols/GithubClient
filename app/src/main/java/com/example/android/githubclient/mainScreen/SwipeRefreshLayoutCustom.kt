package com.example.android.githubclient.mainScreen

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.support.v4.view.ViewCompat.canScrollVertically
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.View
import com.example.android.githubclient.R
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