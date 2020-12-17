package com.jenson.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *  author: CDJenson
 *  date: 2020/9/2 17:24
 *	version: 1.0
 *  description: One-sentence description
 */
class FabScrollBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior(context, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)

        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide(onVisibilityChangedListener)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show(onVisibilityChangedListener)
        }
    }

    private val onVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
            fab?.visibility = View.INVISIBLE
        }

        override fun onShown(fab: FloatingActionButton?) {
            super.onShown(fab)
            fab?.visibility = View.VISIBLE
        }
    }

}
