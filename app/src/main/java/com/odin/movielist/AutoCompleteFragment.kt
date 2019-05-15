package com.odin.movielist

import android.arch.lifecycle.ViewModelProviders
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.odin.movielist.models.Movies
import com.odin.movielist.models.Search
import com.odin.movielist.utils.CoreFragment
import com.odin.movielist.utils.MoviesRestAdapter
import kotlinx.android.synthetic.main.autocomplete_fragment.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */

class AutoCompleteFragment : CoreFragment<AutoCompleteViewModel>() {

    override val layoutResId: Int = R.layout.autocomplete_fragment

    var moviesArrayList: ArrayList<Search> = ArrayList()
    var timer: Timer? = null
    lateinit var movies: Movies
    lateinit var moviesAdapter: MoviesAdapter
    var adapter = MoviesRestAdapter()
    var client = adapter.createService()
    lateinit var call: Call<Movies>
    var query: String = ""

    override fun bindView(viewModel: AutoCompleteViewModel) {
        doAsync {
            bindTextChangeListener()
        }
    }

    private fun bindTextChangeListener() {
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (autoCompleteTextView.isPerformingCompletion) {
                    return
                }
                if (s.length < 5) {
                    return
                }
                if (timer != null) {
                    timer!!.cancel()
                }
                query = s.toString()

                //call = client.getMovies(query,moviesType);
            }

            override fun afterTextChanged(s: Editable) {
                timer = Timer()
                timer!!.schedule(object : TimerTask() {

                    override fun run() {
                        call = client.getMovies(query, moviesType, apiKey)

                        call.enqueue(object : Callback<Movies> {
                            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {

                                movies = response.body()
                                moviesArrayList.clear()
                                if (movies.search == null) {
                                    autoCompleteTextView.completionHint = "No suggestions"
                                    autoCompleteTextView.showDropDown()
                                    return
                                } else
                                    for (i in 0 until movies.search!!.size) {

                                        moviesArrayList.add(movies.search!![i])
                                    }
                                moviesAdapter = MoviesAdapter(activity, moviesArrayList)
                                moviesAdapter.notifyDataSetChanged()

                                Log.d("Call Response2: ", "$call $response ${response.body()} ${response.message()}")
                                Log.d("MoviesArrayList2: ", moviesArrayList.toString())
                                Log.d("MoviesAdapter2: ", moviesAdapter.toString())
                                //   moviesList.setAdapter(moviesAdapter);
                                //moviesAdapter.notifyDataSetChanged();
                                autoCompleteTextView.completionHint = "Search results"
                                autoCompleteTextView.setAdapter(moviesAdapter)
                                autoCompleteTextView.showDropDown()
                                autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                                    run {
                                        Toast.makeText(
                                                context,
                                                moviesArrayList[position].title,
                                                Toast.LENGTH_LONG
                                        ).show()
                                        autoCompleteTextView.text.clear()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Movies>, t: Throwable) {
                                Log.d("Error2: ", call.toString() + " " + t.message + " " + t.cause)
//                                testAdapter.notifyDataSetInvalidated()
                            }
                        })
                    }
                }, 4000)
            }
        })
    }

    override fun provideViewModel(): AutoCompleteViewModel =
            ViewModelProviders.of(this, AutoCompleteViewModel.Factory())
                    .get(AutoCompleteViewModel::class.java)

    companion object {
        const val moviesType = "movie"
        const val apiKey = "f0c03065"
    }
}