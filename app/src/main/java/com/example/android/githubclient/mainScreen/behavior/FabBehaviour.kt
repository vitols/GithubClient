package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout

/**
 * Created by admin on 01.03.2018.
 */
class FabBehaviour(context: Context, attrs: AttributeSet) :
        CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?): Boolean {
        Log.e("Behaviour", (dependency is LinearLayout).toString())
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?): Boolean {
        var dependencyWidth = dependency?.width ?: 0
        var dependencyTranslationY = dependency?.translationY ?: 0f
        Log.e("Behaviour", dependencyWidth.toString() + " " + dependencyTranslationY)
        var translationY: Float = Math.min(0f,  dependencyTranslationY + dependencyWidth)
        child?.translationY = translationY
        return true
    }

}