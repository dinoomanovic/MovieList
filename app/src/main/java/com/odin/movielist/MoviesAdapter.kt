package com.odin.movielist

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.odin.movielist.models.Search
import com.squareup.picasso.Picasso

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */

class MoviesAdapter(context: Activity, private val listData: ArrayList<Search>) : ArrayAdapter<Search>(context, 0, listData), Filterable {
    private val layoutInflater: LayoutInflater? = null

    init {
    }

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

            adapterView = layoutInflater?.inflate(R.layout.movieslist, parent, false)
            holder = ViewHolder()
            val view = LayoutInflater.from(context).inflate(R.layout.autocomplete_fragment, null)

            holder.autoCompleteText = view.findViewById(R.id.textView) as AutoCompleteTextView
            holder.movieTitle = adapterView!!.findViewById(R.id.movieTitle) as TextView
            holder.movieYear = adapterView.findViewById(R.id.movieYear) as TextView
            holder.movieImage = adapterView.findViewById(R.id.movieImage) as ImageView

            adapterView.tag = holder
        } else {
            holder = adapterView.tag as ViewHolder
        }

        //  holder.autoCompleteText.setText(getItem(position).getTitle()+ " " + getItem(position).getYear());
        holder.movieTitle!!.text = getItem(position)!!.title
        if (getItem(position)!!.poster != "N/A") {
            Picasso.with(context).load(getItem(position)!!.poster).resize(200, 200).into(holder.movieImage)
        } else {
            Picasso.with(context).load(android.R.drawable.ic_menu_help).resize(200, 200).into(holder.movieImage)

        }
        holder.movieYear!!.text = getItem(position)!!.year

        return adapterView
    }

    internal class ViewHolder {
        var movieTitle: TextView? = null
        var movieYear: TextView? = null
        var movieImage: ImageView? = null
        var autoCompleteText: AutoCompleteTextView? = null
    }
}
