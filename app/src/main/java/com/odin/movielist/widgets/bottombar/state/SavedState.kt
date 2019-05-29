package com.odin.movielist.widgets.bottombar.state

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class SavedState(
        val selectedItem: Int,
        val superState: Parcelable?
) : Parcelable
