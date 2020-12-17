package com.jenson.core.widget.recyclerView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * author: CDJenson
 * date: 2020/6/19 17:00
 * version: 1.0
 * description: One-sentence description
 */
class SpaceItemDecoration private constructor(private val mOrientation: Int, color: Int, dividerWidth: Int, firstLineVisible: Boolean) :
    ItemDecoration() {
    private var mDividerWidth = 0
    private val mPaint: Paint
    private var mFirstLineVisible = false //是否绘制第一个item的分割线
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when (mOrientation) {
            HORIZONTAL_DIV -> drawHorizontal(c, parent)
            VERTICAL_DIV -> drawVertical(c, parent)
            GRID_DIV -> drawGrid(c, parent)
            else -> drawVertical(c, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val mAdapter = parent.adapter
        if (mAdapter != null) {
            val mChildCount = mAdapter.itemCount
            when (mOrientation) {
                HORIZONTAL_DIV -> {
                    if (!mFirstLineVisible && itemPosition == 0) {
                        return
                    }
                    outRect[mDividerWidth, 0, 0] = 0
                }
                VERTICAL_DIV -> {
                    if (!mFirstLineVisible && itemPosition == 0) {
                        return
                    }
                    outRect[0, mDividerWidth, 0] = 0
                }
                GRID_DIV -> {
                    /**
                     * 表格格局分割线
                     *
                     *
                     * 1：当是第一个Item的时候，四周全部需要分割线
                     * 2：当是第一行Item的时候，需要额外添加顶部的分割线
                     * 3：当是第一列Item的时候，需要额外添加左侧的分割线
                     * 4：默认情况全部添加底部和右侧的分割线
                     *
                     */
                    val mLayoutManager = parent.layoutManager
                    if (mLayoutManager is GridLayoutManager) {
                        val mSpanCount = mLayoutManager.spanCount
                        if (itemPosition == 0) { //1
                            outRect[mDividerWidth, mDividerWidth, mDividerWidth] = mDividerWidth
                        } else if (itemPosition + 1 <= mSpanCount) { //2
                            outRect[0, mDividerWidth, mDividerWidth] = mDividerWidth
                        } else if ((itemPosition + mSpanCount) % mSpanCount == 0) { //3
                            outRect[mDividerWidth, 0, mDividerWidth] = mDividerWidth
                        } else { //4
                            outRect[0, 0, mDividerWidth] = mDividerWidth
                        }
                    }
                }
                else ->                     //纵向布局分割线
                    if (itemPosition != mChildCount - 1) {
                        outRect[0, 0, 0] = mDividerWidth
                    }
            }
        }
    }

    /**
     * 绘制横向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val mChildCount = parent.childCount
        for (i in 0 until mChildCount) {
            val mChild = parent.getChildAt(i)
            drawLeft(c, mChild, parent)
        }
    }

    /**
     * 绘制纵向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val mChildCount = parent.childCount
        for (i in 0 until mChildCount) {
            val mChild = parent.getChildAt(i)
            drawTop(c, mChild, parent)
        }
    }

    /**
     * 绘制表格类型分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private fun drawGrid(c: Canvas, parent: RecyclerView) {
        val mChildCount = parent.childCount
        for (i in 0 until mChildCount) {
            val mChild = parent.getChildAt(i)
            val mLayoutManager = parent.layoutManager
            if (mLayoutManager is GridLayoutManager) {
                val mSpanCount = mLayoutManager.spanCount
                if (i == 0) {
                    drawTop(c, mChild, parent)
                    drawLeft(c, mChild, parent)
                }
                if (i + 1 <= mSpanCount) {
                    drawTop(c, mChild, parent)
                }
                if ((i + mSpanCount) % mSpanCount == 0) {
                    drawLeft(c, mChild, parent)
                }
                drawRight(c, mChild, parent)
                drawBottom(c, mChild, parent)
            }
        }
    }

    /**
     * 绘制右边分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawLeft(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams = mChild.layoutParams as RecyclerView.LayoutParams
        val left = mChild.left - mDividerWidth - mChildLayoutParams.leftMargin
        val top = mChild.top - mChildLayoutParams.topMargin
        val right = mChild.left - mChildLayoutParams.leftMargin
        val bottom: Int
        bottom = if (isGridLayoutManager(recyclerView)) {
            mChild.bottom + mChildLayoutParams.bottomMargin + mDividerWidth
        } else {
            mChild.bottom + mChildLayoutParams.bottomMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 绘制顶部分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawTop(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams = mChild.layoutParams as RecyclerView.LayoutParams
        val left: Int
        val top = mChild.top - mChildLayoutParams.topMargin - mDividerWidth
        val right = mChild.right + mChildLayoutParams.rightMargin
        val bottom = mChild.top - mChildLayoutParams.topMargin
        left = if (isGridLayoutManager(recyclerView)) {
            mChild.left - mChildLayoutParams.leftMargin - mDividerWidth
        } else {
            mChild.left - mChildLayoutParams.leftMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 绘制右边分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawRight(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams = mChild.layoutParams as RecyclerView.LayoutParams
        val left = mChild.right + mChildLayoutParams.rightMargin
        val top: Int
        val right = left + mDividerWidth
        val bottom = mChild.bottom + mChildLayoutParams.bottomMargin
        top = if (isGridLayoutManager(recyclerView)) {
            mChild.top - mChildLayoutParams.topMargin - mDividerWidth
        } else {
            mChild.top - mChildLayoutParams.topMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 绘制底部分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private fun drawBottom(c: Canvas, mChild: View, recyclerView: RecyclerView) {
        val mChildLayoutParams = mChild.layoutParams as RecyclerView.LayoutParams
        val left = mChild.left - mChildLayoutParams.leftMargin
        val top = mChild.bottom + mChildLayoutParams.bottomMargin
        val bottom = top + mDividerWidth
        val right: Int
        right = if (isGridLayoutManager(recyclerView)) {
            mChild.right + mChildLayoutParams.rightMargin + mDividerWidth
        } else {
            mChild.right + mChildLayoutParams.rightMargin
        }
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 判断RecyclerView所加载LayoutManager是否为GridLayoutManager
     *
     * @param recyclerView RecyclerView
     * @return 是GridLayoutManager返回true，否则返回false
     */
    private fun isGridLayoutManager(recyclerView: RecyclerView): Boolean {
        val mLayoutManager = recyclerView.layoutManager
        return mLayoutManager is GridLayoutManager
    }

    class Builer {
        var orientation = VERTICAL_DIV
        var color = Color.TRANSPARENT
        var size = 2
        var firstLineVisible = false

        fun setOrientation(orientation: Int): Builer {
            require(!(orientation != HORIZONTAL_DIV && orientation != VERTICAL_DIV && orientation != GRID_DIV)) { "ItemDecorationPowerful：分割线类型设置异常" }
            this.orientation = orientation
            return this
        }

        fun setColor(color: Int): Builer {
            this.color = color
            return this
        }

        fun setSize(size: Int): Builer {
            this.size = size
            return this
        }

        fun setFirstLineVisible(firstLineVisible: Boolean): Builer {
            this.firstLineVisible = firstLineVisible
            return this
        }

        fun create(): SpaceItemDecoration {
            return SpaceItemDecoration(orientation, color, size, firstLineVisible)
        }
    }

    companion object {
        const val VERTICAL_DIV = 0
        const val HORIZONTAL_DIV = 1
        const val GRID_DIV = 2
    }

    init {
        mDividerWidth = dividerWidth
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mFirstLineVisible = firstLineVisible
    }
}