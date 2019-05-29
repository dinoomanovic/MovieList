package com.odin.movielist.widgets.bottombar.utils

import android.support.annotation.IdRes
import android.support.constraint.ConstraintSet

internal fun ConstraintSet.createChain(
        @IdRes firstItemId: Int,
        @IdRes secondItemId: Int,
        chainStyle: Int
) {
    val chainViews = intArrayOf(firstItemId, secondItemId)
    val chainWeights = floatArrayOf(0f, 0f)

    this.createHorizontalChain(
        firstItemId, ConstraintSet.LEFT,
        secondItemId, ConstraintSet.RIGHT,
        chainViews, chainWeights,
        chainStyle
    )
}
