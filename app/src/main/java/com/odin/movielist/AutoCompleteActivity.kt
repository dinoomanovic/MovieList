package com.odin.movielist

import android.os.Bundle

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */

class AutoCompleteActivity : CoreActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        replaceFragment(AutoCompleteFragment())
    }
}