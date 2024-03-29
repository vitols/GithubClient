package com.example.android.githubclient.mainScreen.decorators

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubclient.R


/**
 * Created by admin on 08.03.2018.
 */
class ItemDecorator : RecyclerView.ItemDecoration {

    private var orientation: Int? = null
    private var divider: Drawable? = null
    private var dividerMarginLeft: Int = 0
    private var dividerMarginRight: Int = 0

    constructor(context: FragmentActivity, orientation: Int, idLeftMargin: Int = 0, idRightMargin: Int = 0) {
        this.orientation = orientation
        divider = ContextCompat.getDrawable(context, R.drawable.item_decoration)
        dividerMarginLeft = if (idLeftMargin != 0) context.resources.getDimension(idLeftMargin).toInt() else 0
        dividerMarginRight = if (idRightMargin != 0) context.resources.getDimension(idRightMargin).toInt() else 0
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if(c == null || parent == null)
            return
        if(orientation == LinearLayoutManager.VERTICAL)
            drawVertical(c, parent)
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = dividerMarginLeft
        val right = parent.width - dividerMarginRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val bottom = child.bottom + params.bottomMargin
            val top = bottom + (divider?.getIntrinsicHeight() ?: 0)
            divider?.setBounds(left, bottom, right, top)
            divider?.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent?.getChildAdapterPosition(view) == 0) {
            return
        }
        orientation = (parent?.layoutManager as LinearLayoutManager).orientation
        if(orientation == LinearLayoutManager.VERTICAL) {
            outRect?.right = divider?.intrinsicHeight
        }
        else
            outRect?.left = divider?.intrinsicWidth;
    }
}