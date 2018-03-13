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
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * Created by admin on 19.02.2018.
 */
class AvatarBehavior(var context: Context, var attrs: AttributeSet) :
        CoordinatorLayout.Behavior<CircleImageView>(context, attrs) {

    private var avatarStartSize = 0.0f
    private var avatarStartX = 0.0f
    private var avatarStartY = 0.0f
    private var avatarStartMarginTop = 0.0f
    private var avatarFinalSize = 0.0f
    private var avatarFinalX = 0.0f
    private var avatarFinalY = 0.0f
    private var avatarFinalMargin = 0.0f

    private var nestedScrollStartY = 0.0f
    private var nestedScrollStartMarginTop = 0.0f

    private var appBarHeight = 0.0f
    init {

        if(attrs != null) {
            val appBarAttr = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
            appBarHeight = appBarAttr.getDimension(0,0f)
            appBarAttr.recycle()
        }

        avatarStartSize = context.resources.getDimension(R.dimen.max_avatar_size)
        avatarFinalMargin = context.resources.getDimension(R.dimen.avatar_final_top_margin)
        avatarStartMarginTop = context.resources.getDimension(R.dimen.avatar_margin_top)
        avatarStartY = appBarHeight + avatarStartMarginTop
        avatarFinalSize = appBarHeight - 2 * avatarFinalMargin
        avatarFinalY = avatarFinalMargin

        nestedScrollStartMarginTop = context.resources.getDimension(R.dimen.nested_scroll_margin_top)
        nestedScrollStartY = nestedScrollStartMarginTop + appBarHeight

        var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        avatarFinalX = windowManager.defaultDisplay.width - avatarFinalSize - avatarFinalMargin
        avatarStartX = windowManager.defaultDisplay.width / 2 - avatarStartSize / 2

    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        var nestedScroll = target as NestedScrollView
        val params = child.layoutParams as CoordinatorLayout.LayoutParams

        if (nestedScroll.scrollY <= 0) {
            params.height = avatarStartSize.toInt()
            params.width = avatarStartSize.toInt()
            child.layoutParams = params

            child.x = avatarStartX
            child.y = avatarStartY
        } else {
            if (nestedScroll.scrollY >= nestedScrollStartMarginTop) {
                params.height = avatarFinalSize.toInt()
                params.width = avatarFinalSize.toInt()
                child.layoutParams = params

                child.x = avatarFinalX
                child.y = avatarFinalY

            } else {

                var minMaxRatioPercent = avatarFinalSize / avatarStartSize * 100
                var percentageOfDistance = 0f
                var newSize = 0

                percentageOfDistance = minMaxRatioPercent + (1 - nestedScroll.scrollY / nestedScrollStartMarginTop) * (100 - minMaxRatioPercent)
                newSize = (percentageOfDistance * avatarStartSize / 100).toInt()

                /*Log.e("newSize", newSize.toString())
                Log.e("maxSize", avatarStartSize.toString())
                Log.e("distance", (nestedScrollStartMarginTop - nestedScroll.scrollY).toString())*/

                params.height = newSize
                params.width = newSize
                child.layoutParams = params

                var minMaxYRatio = avatarFinalY / avatarStartY * 100
                var percentageOfY = minMaxYRatio + (1 - nestedScroll.scrollY / nestedScrollStartMarginTop) * (100 - minMaxYRatio)
                var newY = percentageOfY * avatarStartY / 100
                child.y = newY

                var minMaxXRatio = avatarFinalX / avatarStartX * 100
                var percentageOfX = minMaxXRatio + (1 - nestedScroll.scrollY / nestedScrollStartMarginTop) * (100 - minMaxXRatio)
                var newX = percentageOfX * avatarStartX / 100
                child.x = newX
            }
        }

    }

}