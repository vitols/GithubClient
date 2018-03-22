package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by admin on 21.03.2018.
 */
class BottomBarBehavior(context: Context, attributeSet: AttributeSet): CoordinatorLayout.Behavior<BottomNavigationView>(context, attributeSet) {
    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView, target: View, velocityX: Float, velocityY: Float): Boolean {
        if (velocityY < 0)
            child.animate().translationYBy(child.height * 1f)
        else
            child.animate().translationYBy(0f)
        return true
    }
}