package com.odin.movielist.widgets.bottombar.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.odin.movielist.widgets.bottombar.utils.clamp

class ExpandableBottomBarScrollableBehavior<V: View>:
        ExpandableBottomBarBehavior<V> {

    constructor(): super()

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = clamp(child.translationY + dy, 0f, child.height.toFloat())
    }
}
