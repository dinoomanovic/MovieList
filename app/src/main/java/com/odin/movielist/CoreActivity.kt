package com.odin.movielist

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.view.Menu
import android.view.MenuItem

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */

open class CoreActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        menuInflater.inflate(R.menu.mainmenu, menu)
        //return super.onCreateOptionsMenu(menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchMovies -> {
                val intent = Intent(this, AutoCompleteActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.contentMain, fragment, fragment.javaClass.simpleName)
        transaction.addToBackStack(backStateName)
        try {
            transaction.commitAllowingStateLoss()
        } catch (ex: Exception) {
            this.finish()
        }

    }
}