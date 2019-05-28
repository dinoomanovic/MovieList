package com.odin.movielist.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odin.movielist.MainActivity

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */
abstract class CoreFragment<VIEW_MODEL : ViewModel> : Fragment() {
    abstract val layoutResId: Int
    abstract fun bindView(viewModel: VIEW_MODEL)
    abstract fun provideViewModel(): VIEW_MODEL
    private lateinit var viewModel: VIEW_MODEL
    var dbState = MutableLiveData<State>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = provideViewModel()
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView(viewModel)
    }

    fun loadFragment(fragment: Fragment) {
        try {
            (activity as MainActivity).replaceFragment(fragment)
        } catch (ex: Exception) {
            Log.e(TAG, "Error replacing Fragment!", ex)
        }
    }

    protected fun closeActivity() {
        (activity as MainActivity).finish()
    }

    enum class State {
        INSERT, CLEAR
    }

    companion object {
        private const val TAG = "CoreFragment"
    }
}