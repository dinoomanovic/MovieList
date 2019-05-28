package com.odin.movielist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */
class AutoCompleteViewModel : ViewModel() {
    class Factory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <VIEW_MODEL : ViewModel?> create(modelClass: Class<VIEW_MODEL>): VIEW_MODEL {
            return AutoCompleteViewModel() as VIEW_MODEL
        }
    }
}