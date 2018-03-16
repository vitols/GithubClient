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
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.android.githubclient.R
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * Created by admin on 19.02.2018.
 */
class AvatarBehavior(var context: Context, attrs: AttributeSet) :
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

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        avatarFinalX = windowManager.defaultDisplay.width - avatarFinalSize - avatarFinalMargin
        avatarStartX = windowManager.defaultDisplay.width / 2 - avatarStartSize / 2

    }



    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        val nestedScroll = target as NestedScrollView
        /*Log.e("scrollY", nestedScroll.scrollY.toString())
        Log.e("Y", nestedScroll.y.toString())*/
        val params = child.layoutParams as CoordinatorLayout.LayoutParams

        if (nestedScroll.scrollY <= 0) {
            params.height = avatarStartSize.toInt()
            params.width = avatarStartSize.toInt()
            child.layoutParams = params

            child.y = avatarStartY
            child.x = avatarStartX
        } else {
            if (nestedScroll.scrollY >= nestedScrollStartMarginTop) {
                params.height = avatarFinalSize.toInt()
                params.width = avatarFinalSize.toInt()
                child.layoutParams = params

                child.y = avatarFinalY
                child.x = avatarFinalX

            } else {

                val minMaxRatioPercent = avatarFinalSize / avatarStartSize * 100
                var percentageOfDistance: Float
                var newSize: Int

                percentageOfDistance = minMaxRatioPercent + (1 - nestedScroll.scrollY / nestedScrollStartMarginTop) * (100 - minMaxRatioPercent)
                newSize = (percentageOfDistance * avatarStartSize / 100).toInt()

                /*Log.e("newSize", newSize.toString())
                Log.e("maxSize", avatarStartSize.toString())
                Log.e("distance", (nestedScrollStartMarginTop - nestedScroll.scrollY).toString())*/

                params.height = newSize
                params.width = newSize
                child.layoutParams = params


                val minMaxXRatio = avatarFinalX / avatarStartX * 100
                val percentageOfX = minMaxXRatio + (1 - nestedScroll.scrollY / nestedScrollStartMarginTop) * (100 - minMaxXRatio)
                val newX = percentageOfX * avatarStartX / 100

                val minMaxYRatio = avatarFinalY / avatarStartY * 100
                val percentageOfY = minMaxYRatio + (1 - nestedScroll.scrollY / nestedScrollStartMarginTop) * (100 - minMaxYRatio)
                val newY = percentageOfY * avatarStartY / 100

                child.y = newY
                child.x = newX
            }
        }

    }

}