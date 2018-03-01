package com.example.android.githubclient.mainScreen.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by admin on 19.02.2018.
 */
class AvatarToRightTopCornerBehaviour(context: Context, attrs: AttributeSet) :
        CoordinatorLayout.Behavior<CircleImageView>(context, attrs) {

    private var imageFinalX = 0.0f;
    private var imageFinalY = 0.0f;
    private var nestedChildPosition = 0.0f;
    init {
        //TODO: Find toolbar's coords(C) and size(S), C + S - (Image.Size) - Image.padding
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView,
                                     directTargetChild: View, target: View, axes: Int, type: Int): Boolean {

        nestedChildPosition = (target as NestedScrollView).getChildAt(0).x
        Log.e("onStartNestedScroll", nestedChildPosition.toString())
        return axes === ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)

    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: CircleImageView,
                                target: View, dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        /*Log.e("onNestedScroll", (target as NestedScrollView).getChildAt(0).toString())
        child.setY(child.y - 5)
        child.setX(child.x + 5)
        var childParams = child.layoutParams as CoordinatorLayout.LayoutParams
        childParams.height -= 5
        childParams.width -= 5
        child.layoutParams = childParams*/
    }

}