package com.odin.movielist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.odin.movielist.Models.Movies;
import com.odin.movielist.Models.Search;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mobil on 16.03.2017.
 */

public class MoviesAdapter extends ArrayAdapter<Search> implements Filterable {
    private ArrayList<Search> listData;
    private Context context;
    private String log;
    private LayoutInflater layoutInflater;
    public MoviesAdapter(Activity context, ArrayList<Search> listData){
       super(context,0,listData);
        this.listData = listData;
        this.context = context;



    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Search getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convertView = layoutInflater.inflate(R.layout.weather_layout,null);

        ViewHolder holder;
        /*if(convertView == null){
        holder = new ViewHolder();

        holder.cityName = (TextView) convertView.findViewById(R.id.placeId);
        holder.temp = (TextView) convertView.findViewById(R.id.tempId);
            convertView.setTag(holder);
    } else {
        holder = (ViewHolder) convertView.getTag();
    }*/
        if(convertView == null){

            convertView = layoutInflater.from(context).inflate(R.layout.movieslist, parent,false);
            holder = new ViewHolder();
            View view = LayoutInflater.from(context).inflate(R.layout.autocomplete,null);

            holder.autoCompleteText = (AutoCompleteTextView) view.findViewById(R.id.textView);
            holder.movieTitle=(TextView) convertView.findViewById(R.id.movieTitle);
            holder.movieYear =(TextView) convertView.findViewById(R.id.movieYear);

            holder.movieImage = (ImageView) convertView.findViewById(R.id.movieImage);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
      //  if(listData.size()<0){
            //    CityPreferences cityPreferences = new
            // holder.cityName.setText("No_data");
      //      holder.movieTitle.setText("");

        }



         //  holder.autoCompleteText.setText(getItem(position).getTitle()+ " " + getItem(position).getYear());
        holder.movieTitle.setText(getItem(position).getTitle());
        if(!getItem(position).getPoster().equals("N/A")) {
            Picasso.with(context).load(getItem(position).getPoster()).resize(200, 200).into(holder.movieImage);
        }
        else{
            Picasso.with(context).load(android.R.drawable.ic_menu_help).resize(200, 200).into(holder.movieImage);

        }
        holder.movieYear.setText(getItem(position).getYear());

            //setLog(weather.getName()+" "+weather.getMain().getTemp()+" "+weather.getBase());
          //holder.movieTitle.setText(movies.getCity().getName());
         //   holder.movieYear.setText(Math.round(weather.getList().get(0).getMain().getTemp()) + " °C");
/*
            Drawable img = ContextCompat.getDrawable(convertView.getContext(),
                    getIcon(weather.getList().get(0).getWeather().get(0).getIcon()+""));
            holder.icon.setImageDrawable(img);*/
            // holder.humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "");
            //  holder.pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "");
            // holder.wind.setText("Wind: " + weather.wind.getSpeed() + "");



        return convertView;
    }
    static class ViewHolder{
        public TextView movieTitle;
        public TextView movieYear;
        public ImageView movieImage;
        public AutoCompleteTextView autoCompleteText;

    }




}
