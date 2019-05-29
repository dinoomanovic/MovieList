package com.odin.movielist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odin.movielist.models.Search
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movieslist.view.*

/**
 * Created by Dino Omanovic on Mar 16, 2017
 */
//class MoviesAdapter(context: Activity, private val listData: ArrayList<Search>) : ArrayAdapter<Search>(context, 0, listData), Filterable {
class MoviesAdapter(
        private val moviesList: ArrayList<Search>,
        private val onItemLongClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(
                        R.layout.movieslist,
                        parent,
                        false
                )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(moviesList[position])

        viewHolder.itemView.setOnLongClickListener {
            onItemLongClickListener.onItemLongClick(viewHolder.itemView, position)
            return@setOnLongClickListener true
        }
    }

    fun getItem(position: Int): Search = moviesList[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Search) {
            if (item.Poster != "N/A") {
                Picasso.with(itemView.context).load(item.Poster).resize(200, 200).into(itemView.movieImage)
            } else {
                Picasso.with(itemView.context).load(android.R.drawable.ic_menu_help).resize(200, 200).into(itemView.movieImage)

            }
            itemView.movieTitle.text = item.Title
            itemView.movieYear.text = item.Year
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }
}
