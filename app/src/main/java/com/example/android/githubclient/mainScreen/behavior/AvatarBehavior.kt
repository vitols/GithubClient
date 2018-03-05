package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import de.hdodenhof.circleimageview.CircleImageView
import android.opengl.ETC1.getHeight
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.android.githubclient.R


/**
 * Created by admin on 19.02.2018.
 */
class AvatarBehavior(var context: Context, var attrs: AttributeSet) :
        CoordinatorLayout.Behavior<CircleImageView>(context, attrs) {

    private var avatarStartSize = 0.0f
    private var avatarStartMarginTop = 0.0f
    private var avatarFinalMargin = 0.0f
    private var appBarHeight = 0.0f
    private var distance = 0.0f
    private var appBarWidth = 0.0f
    private var finalNestedScrollY = 0.0f
    init {
        //TODO: Find toolbar's coords(C) and size(S), C + S - (Image.Size) - Image.padding
        if(attrs != null) {
            val attr = context.obtainStyledAttributes(attrs, R.styleable.AvatarBehavior)
            avatarFinalMargin = attr.getDimension(R.styleable.AvatarBehavior_avatarFinalMargin, 0f)
            attr.recycle()

            val appBarAttr = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
            appBarHeight = appBarAttr.getDimension(0,0f)
            appBarAttr.recycle()
        }
        avatarStartSize = context.resources.getDimension(R.dimen.max_avatar_size)
        avatarStartMarginTop = context.resources.getDimension(R.dimen.avatar_margin_top)
        distance = avatarStartSize + avatarStartMarginTop
        //Log.e("threshold", threshold.toString())/
        //Log.e("avatarMargin", avatarStartMarginTop.toString())

    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return type == ViewCompat.TYPE_TOUCH
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        //val diff = child.bottom - nestedContent.top
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        var nestedScroll = target as NestedScrollView
        val params = child.layoutParams as CoordinatorLayout.LayoutParams
        Log.e("dyConsumed", dyConsumed.toString())

        if(dyConsumed > 0)
        {
            if (nestedScroll.scrollY + dyConsumed < distance) {

                child.y -= 1
                child.x += 1

            } else  {
                params.height = appBarHeight.toInt()
                params.width = appBarHeight.toInt()
                params.setMargins(0, avatarFinalMargin.toInt(), avatarFinalMargin.toInt(), avatarFinalMargin.toInt())
                child.layoutParams = params
                child.y -= 1
                child.x += 1
            }
        } else if (nestedScroll.scrollY - dyConsumed < distance) {

                child.y += 1
                child.x -= 1

            } else  {
                params.height = appBarHeight.toInt()
                params.width = appBarHeight.toInt()
                params.setMargins(0, avatarFinalMargin.toInt(), avatarFinalMargin.toInt(), avatarFinalMargin.toInt())
                child.layoutParams = params
            }

    }

    fun startOfView(view: NestedScrollView, child: CircleImageView): Boolean {

            Log.e("startOfView", "diff is <")
        return false
    }

/*    override fun layoutDependsOn(parent: CoordinatorLayout?, child: CircleImageView?, dependency: View?): Boolean {
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: CircleImageView?, dependency: View?): Boolean {
        if(child == null || dependency == null)
            return false

        var params = child.layoutParams as CoordinatorLayout.LayoutParams

        val diff = child.bottom - dependency.top
        Log.e("viewChanged", diff.toString())
        if(diff < 30) {
            params.width -=10
            params.height -=10
            child.layoutParams = params
            return true
        }
        return false

    }*/

}