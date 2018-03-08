package com.example.android.githubclient.mainScreen.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.design.R.attr.divider
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v4.content.ContextCompat
import com.example.android.githubclient.base.presentation.model.User


/**
 * Created by admin on 08.03.2018.
 */
class UserItemDecorator : RecyclerView.ItemDecoration {

    /*constructor(context: Context) {
        *//*val styledAttributes = context.obtainStyledAttributes(ATTRS);
        divider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();*//*
    }*/
    private var divider: Drawable? = null

    constructor(context: Context, resId: Int) {
        divider = ContextCompat.getDrawable(context, resId)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + 10
        val right = parent.width - parent.paddingRight + 10

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider?.intrinsicHeight ?: 0)

            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }
}