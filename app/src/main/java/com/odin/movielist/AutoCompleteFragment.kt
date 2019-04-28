package com.odin.movielist

import android.arch.lifecycle.ViewModelProviders
import com.odin.movielist.utils.CoreFragment

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */

class AutoCompleteFragment : CoreFragment<AutoCompleteViewModel>() {

    override val layoutResId: Int = R.layout.autocomplete_fragment

    override fun bindView(viewModel: AutoCompleteViewModel) {
    }

    override fun provideViewModel(): AutoCompleteViewModel =
            ViewModelProviders.of(this, AutoCompleteViewModel.Factory())
                    .get(AutoCompleteViewModel::class.java)
}