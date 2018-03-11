package com.example.android.githubclient.mainScreen.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.design.R.attr.divider
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.android.githubclient.R
import com.example.android.githubclient.base.presentation.model.User


/**
 * Created by admin on 08.03.2018.
 */
class ItemDecorator : RecyclerView.ItemDecoration {

    /*constructor(context: Context) {
        *//*val styledAttributes = context.obtainStyledAttributes(ATTRS);
        divider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();*//*
    }*/
    private var orientation: Int? = null
    private var divider: Drawable? = null
    private var dividerMargin: Int = 0

    constructor(context: Context) {
        divider = ContextCompat.getDrawable(context, R.drawable.item_user_decoration);
        dividerMargin = context.resources.getDimension(R.dimen.item_user_divider_margin).toInt()

    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if(c == null || parent == null)
            return
        //if(orientation == LinearLayoutManager.VERTICAL)
            drawVertical(c, parent)
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + dividerMargin
        val right = parent.width - parent.paddingRight - dividerMargin

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val bottom = child.bottom + params.bottomMargin;
            val top = bottom + (divider?.getIntrinsicHeight() ?: 0);
            divider?.setBounds(left, bottom, right, top)
            divider?.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent?.getChildAdapterPosition(view) == 0) {
            return;
        }
        orientation = (parent?.layoutManager as LinearLayoutManager).orientation
        /*if(orientation == LinearLayoutManager.VERTICAL) {
            outRect?.right = divider?.intrinsicHeight
        }
        else
            outRect?.left = divider?.intrinsicWidth;*/
    }
}