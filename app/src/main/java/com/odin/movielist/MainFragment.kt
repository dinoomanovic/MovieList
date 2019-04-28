package com.odin.movielist

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.odin.movielist.models.Movies
import com.odin.movielist.models.Search
import com.odin.movielist.utils.CoreFragment
import com.odin.movielist.utils.MoviesRestAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Dino Omanovic on Apr 27, 2019
 */

class MainFragment : CoreFragment<MainViewModel>() {
    override val layoutResId: Int = R.layout.main_fragment

    var moviesArrayList = ArrayList<Search>()
    lateinit var movies: Movies
    lateinit var moviesAdapter: MoviesAdapter

    override fun bindView(viewModel: MainViewModel) {
        val adapter = MoviesRestAdapter()
        val client = adapter.createService()
        val call = client.getMovies("Revenant", moviesType)
        //   call.enqueue(new GetMovies(moviesList));
        progressBar.visibility = View.GONE
    }

    inner class GetMovies(private val moviesList: ListView) : Callback<Movies> {
        override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
            movies = response.body()
            for (i in 0 until movies.search!!.size) {
                moviesArrayList.add(movies.search!![i])
            }
            moviesAdapter = MoviesAdapter(this@MainFragment.activity, moviesArrayList)
            Log.d("Call Response: ", call.toString() + " " + response.toString() + " " + response.body().toString() + " " + response.message())
            //            Log.d("WeatherData: ", weatherData.getCity().getName() + " " + weatherData.getList().get(0).getMain().getTemp()+ " " +weatherData.getList().get(0).getWeather());
            Log.d("MoviesArrayList: ", moviesArrayList.toString())
            Log.d("MoviesAdapter: ", moviesAdapter.toString())
            moviesList.onItemLongClickListener =
                    AdapterView.OnItemLongClickListener { _, _, position, _ ->
                        AlertDialog.Builder(this@MainFragment.activity)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are you sure ?")
                                .setMessage("Do you want to delete this item ?")
                                .setPositiveButton("Yes") { _, _ ->
                                    moviesArrayList.removeAt(position)
                                    moviesAdapter.notifyDataSetChanged()
                                    //   SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.pi.notesapp", Context.MODE_PRIVATE);
                                    //    HashSet<String> set = new HashSet(MainActivity.notes);
                                    //   sharedPreferences.edit().putStringSet("notes", set).apply();
                                }.setNegativeButton("No", null).show()

                        true
                    }
            moviesList.adapter = moviesAdapter
            // autoCompleteTextView.setAdapter(moviesAdapter);
            progressBar.visibility = View.GONE
            moviesAdapter.notifyDataSetChanged()
        }

        override fun onFailure(call: Call<Movies>, t: Throwable) {
            progressBar.visibility = View.GONE
            Log.d("Error: ", call.toString() + " " + t.message + " " + t.cause)
        }
    }

    override fun provideViewModel(): MainViewModel =
            ViewModelProviders.of(this, MainViewModel.Factory())
                    .get(MainViewModel::class.java)

    companion object {
        const val moviesType = "movie"
    }
}