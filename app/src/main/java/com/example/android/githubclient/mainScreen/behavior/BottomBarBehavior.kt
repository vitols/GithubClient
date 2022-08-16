package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


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