package com.odin.movielist

import android.os.Bundle
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */
class MainActivity : CoreActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        expandableBar.onItemSelectedListener = { _, menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(MainFragment())
                }
                R.id.search -> {
                    replaceFragment(AutoCompleteFragment())
                }
            }
        }
        replaceFragment(MainFragment())
    }
}

