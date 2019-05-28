package com.odin.movielist

import android.os.Bundle
import android.view.MenuItem

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */
class AutoCompleteActivity : CoreActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autocomplete_activity)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(AutoCompleteFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
        // Press Back Icon
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}