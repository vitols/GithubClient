package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout



/**
 * Created by admin on 01.03.2018.
 */
class FabBehavior(context: Context, attrs: AttributeSet) :
        CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?): Boolean {
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?): Boolean {
        if(dependency == null || child == null)
            return false
        //val translationX = Math.min(0f, dependency.translationX - dependency.width)
        child.translationX = dependency.translationX
        return true
    }

}