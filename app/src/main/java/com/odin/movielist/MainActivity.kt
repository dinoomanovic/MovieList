package com.odin.movielist

import android.os.Bundle

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */
class MainActivity : CoreActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        replaceFragment(MainFragment())
    }
}

