package com.odin.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.odin.movielist.Models.Movies;
import com.odin.movielist.Models.Search;
import com.odin.movielist.Utils.MoviesApi;
import com.odin.movielist.Utils.MoviesRestAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.R.id.parent;


/**
 * Created by mobil on 16.03.2017.
 */

public class AutoComplete extends AppCompatActivity {
    public ArrayList<Search> moviesArrayList = new ArrayList<>();
    public static final String moviesType="movie";
    public String[] stringArray = new String[10];
    private Object lock = new Object();
    Timer timer;
    public Movies movies;
    public MoviesAdapter moviesAdapter;
    MoviesRestAdapter adapter = new MoviesRestAdapter();
    MoviesApi client = adapter.createService();
    Call<Movies> call;
    AutoCompleteTextView autoCompleteTextView ;
   ArrayAdapter<String> testadapter;
    String query;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete);
        testadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stringArray);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.textView);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
          if(autoCompleteTextView.isPerformingCompletion()){

              return;
            }
        if(s.length() <5) {


            return;
        }
                if (timer != null) {
                    timer.cancel();
                }
              query = s.toString();

                 //call = client.getMovies(query,moviesType);


            }

            @Override
            public void afterTextChanged(Editable s) {
                    timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(client.getMovies(query, moviesType) != null){
                        call = client.getMovies(query, moviesType);

                        call.enqueue(new Callback<Movies>() {
                            // private ListView moviesList;


                            @Override
                            public void onResponse(Call<Movies> call, Response<Movies> response) {

                                movies = response.body();

                                    moviesArrayList.clear();
                                if(movies.getSearch() == null){
                                    autoCompleteTextView.setCompletionHint("No suggestions");
                                    autoCompleteTextView.showDropDown();
                                    return;
                                }
                                else
                                    for (int i = 0; i < movies.getSearch().size(); i++) {

                                        moviesArrayList.add(movies.getSearch().get(i));
                                        stringArray[i] = movies.getSearch().get(i).getTitle();

                                    }
                                    moviesAdapter = new MoviesAdapter(AutoComplete.this, moviesArrayList);
                                    moviesAdapter.notifyDataSetChanged();

                                    Log.d("Call Response2: ", call.toString() + " " + response.toString() + " " + response.body().toString() + " " + response.message());
//            Log.d("WeatherData: ", weatherData.getCity().getName() + " " + weatherData.getList().get(0).getMain().getTemp()+ " " +weatherData.getList().get(0).getWeather());
                                    Log.d("MoviesArrayList2: ", moviesArrayList.toString());
                                    Log.d("MoviesAdapter2: ", moviesAdapter.toString());
                                    //   moviesList.setAdapter(moviesAdapter);


                                    //moviesAdapter.notifyDataSetChanged();

                                    //
                                    autoCompleteTextView.setCompletionHint("Search results");
                                    autoCompleteTextView.setAdapter(moviesAdapter);
                                    autoCompleteTextView.showDropDown();

                            }
                            @Override
                            public void onFailure(Call<Movies> call, Throwable t) {
                                Log.d("Error2: ", call.toString() + " " + t.getMessage() + " " + t.getCause());
                                testadapter.notifyDataSetInvalidated();
                            }
                        });



                    }
                    else{
                            autoCompleteTextView.setText("No suggestions");
                        }
                    }
                },4000);



                }


        });



    }

}
