package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.android.githubclient.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by admin on 06.03.2018.
 */
class ExitButtonBehavior(var context: Context, var attrs: AttributeSet) :
        CoordinatorLayout.Behavior<ImageView>(context, attrs) {

    private var appBarHeight = 0.0f

    private var avatarStartMarginTop = 0.0f
    private var avatarFinalMargin = 0.0f
    private var avatarStartY = 0.0f

    init {
        if(attrs != null) {
            val attr = context.obtainStyledAttributes(attrs, R.styleable.AvatarBehavior)
            avatarFinalMargin = attr.getDimension(R.styleable.AvatarBehavior_avatarFinalMargin, 0f)
            attr.recycle()

            val appBarAttr = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
            appBarHeight = appBarAttr.getDimension(0,0f)
            appBarAttr.recycle()
        }
        avatarStartMarginTop = context.resources.getDimension(R.dimen.avatar_margin_top)
        avatarStartY = avatarStartMarginTop + appBarHeight

    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: ImageView?, dependency: View?): Boolean {
        return dependency is CircleImageView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: ImageView?, dependency: View?): Boolean {
        if(dependency == null || child == null)
            return false

        if(avatarStartY == dependency.y){
            if(child.visibility == View.GONE)
                child.visibility = View.VISIBLE
            child.alpha = 1f
            return true
        }
        else {
            /*Log.e("Y", dependency.y.toString())
            Log.e("startY", avatarStartY.toString())
            Log.e("ratio", (dependency.y / avatarStartY).toString())*/

            if(child.visibility == View.GONE)
                child.visibility = View.VISIBLE
            child.alpha = 1.0f * dependency.y / avatarStartY

            if(dependency.y  <= avatarStartY / 2) {
                child.visibility = View.GONE
            }
            return true
        }
    }
}