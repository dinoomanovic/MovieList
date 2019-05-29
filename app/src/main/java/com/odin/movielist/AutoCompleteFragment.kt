package com.odin.movielist

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.odin.movielist.models.Movies
import com.odin.movielist.models.MoviesDatabase
import com.odin.movielist.models.MoviesResponseDao
import com.odin.movielist.models.Search
import com.odin.movielist.utils.CoreFragment
import com.odin.movielist.utils.MoviesRestAdapter
import com.squareup.picasso.Picasso
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
    lateinit var moviesAdapter: MoviesListAdapter
    var adapter = MoviesRestAdapter()
    var client = adapter.createService()
    lateinit var call: Call<Movies>
    var query: String = ""
    private var db: MoviesDatabase? = null
    private var moviesResponseDao: MoviesResponseDao? = null

    override fun bindView(viewModel: AutoCompleteViewModel) {
            bindTextChangeListener()
    }

    private fun bindTextChangeListener() {
        db = MoviesDatabase.invoke(context!!)
        moviesResponseDao = db?.moviesResponseDao()
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
                                if ((activity == null) && activity!!.isFinishing && !isAdded) return
                                movies = response.body()
                                moviesArrayList.clear()
                                if (movies.search == null) {
                                    autoCompleteTextView.completionHint = "No suggestions"
                                    autoCompleteTextView.setHintTextColor(Color.RED)
                                    return
                                } else
                                    for (i in 0 until movies.search!!.size) {

                                        moviesArrayList.add(movies.search!![i])
                                    }
                                moviesAdapter = MoviesListAdapter(activity!!, moviesArrayList)
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
                                    Toast.makeText(
                                            context,
                                            moviesArrayList[position].Title,
                                            Toast.LENGTH_LONG
                                    ).show()
                                    doAsync {
                                        moviesResponseDao!!.insertMovie(movies.search!![position])
                                        dbState.value = (State.INSERT)
                                    }
                                    autoCompleteTextView.text.clear()
                                }
                            }

                            override fun onFailure(call: Call<Movies>, t: Throwable) {
                                autoCompleteTextView.completionHint = "Network error"
                                Log.d("Error2: ", call.toString() + " " + t.message + " " + t.cause)
//                                testAdapter.notifyDataSetInvalidated()
                            }
                        })
                    }
                }, 4000)
            }
        })
    }

    class MoviesListAdapter(context: Activity, private val listData: ArrayList<Search>) : ArrayAdapter<Search>(context, 0, listData), Filterable {
            override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(position: Int): Search? {
        return listData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var adapterView = convertView

        val holder: ViewHolder
        if (adapterView == null) {

            adapterView = LayoutInflater.from(context).inflate(R.layout.movieslist, parent, false)
            holder = ViewHolder()
            val view = LayoutInflater.from(context).inflate(R.layout.autocomplete_fragment, null)

            holder.autoCompleteText = view.findViewById(R.id.autoCompleteTextView) as AutoCompleteTextView
            holder.movieTitle = adapterView!!.findViewById(R.id.movieTitle) as TextView
            holder.movieYear = adapterView.findViewById(R.id.movieYear) as TextView
            holder.movieImage = adapterView.findViewById(R.id.movieImage) as ImageView

            adapterView.tag = holder
        } else {
            holder = adapterView.tag as ViewHolder
        }

        //  holder.autoCompleteText.setText(getItem(position).getTitle()+ " " + getItem(position).getYear());
        holder.movieTitle!!.text = getItem(position)!!.Title
        if (getItem(position)!!.Poster != "N/A") {
            Picasso.with(context).load(getItem(position)!!.Poster).resize(200, 200).into(holder.movieImage)
        } else {
            Picasso.with(context).load(android.R.drawable.ic_menu_help).resize(200, 200).into(holder.movieImage)

        }
        holder.movieYear!!.text = getItem(position)!!.Year

        return adapterView
    }

    internal class ViewHolder {
        var movieTitle: TextView? = null
        var movieYear: TextView? = null
        var movieImage: ImageView? = null
        var autoCompleteText: AutoCompleteTextView? = null
    }
    }

    override fun provideViewModel(): AutoCompleteViewModel =
            ViewModelProviders.of(this, AutoCompleteViewModel.Factory())
                    .get(AutoCompleteViewModel::class.java)

    companion object {
        const val moviesType = "movie"
        const val apiKey = "f0c03065"
    }
}