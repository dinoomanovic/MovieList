package com.odin.movielist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.odin.movielist.Models.Movies;
import com.odin.movielist.Models.Search;
import com.odin.movielist.Utils.MoviesApi;
import com.odin.movielist.Utils.MoviesRestAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Search> moviesArrayList = new ArrayList<>();
    public static final String moviesType="movie";
    public Movies movies;
    public ProgressBar progressBar;
    public MoviesAdapter moviesAdapter;
       @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          progressBar = (ProgressBar) findViewById(R.id.progressBar);
        MoviesRestAdapter adapter = new MoviesRestAdapter();
        MoviesApi client = adapter.createService();
        final ListView moviesList = (ListView) findViewById(R.id.movieList);
     Call<Movies> call = client.getMovies("Revenant",moviesType);
    //   call.enqueue(new GetMovies(moviesList));
       progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        if(menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        menuInflater.inflate(R.menu.mainmenu, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMovies:

                Intent intent = new Intent(this, AutoComplete.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class GetMovies implements Callback<Movies> {
        private ListView moviesList;
        public GetMovies(ListView moviesList){
            this.moviesList = moviesList;
        }
        @Override
        public void onResponse(Call<Movies> call, Response<Movies> response) {
           // View view = LayoutInflater.from(getApplication()).inflate(R.layout.movieslist,null);
            //AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.textView3);
            movies=response.body();
            for(int i = 0; i< movies.getSearch().size(); i++) {
                moviesArrayList.add(movies.getSearch().get(i));
            }
            moviesAdapter = new MoviesAdapter(MainActivity.this ,moviesArrayList);
            Log.d("Call Response: ", call.toString() + " "  + response.toString() + " " +  response.body().toString() + " " + response.message());
//            Log.d("WeatherData: ", weatherData.getCity().getName() + " " + weatherData.getList().get(0).getMain().getTemp()+ " " +weatherData.getList().get(0).getWeather());
            Log.d("MoviesArrayList: ", moviesArrayList.toString());
            Log.d("MoviesAdapter: ", moviesAdapter.toString());
            moviesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final int itemToDelete = position;
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you sure ?")
                            .setMessage("Do you want to delete this item ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    moviesArrayList.remove(itemToDelete);
                                    moviesAdapter.notifyDataSetChanged();
                                 //   SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.pi.notesapp", Context.MODE_PRIVATE);

                                //    HashSet<String> set = new HashSet(MainActivity.notes);

                                 //   sharedPreferences.edit().putStringSet("notes", set).apply();
                                }
                            }).setNegativeButton("No",null).show();

                    return true;
                }
            });
            moviesList.setAdapter(moviesAdapter);
           // autoCompleteTextView.setAdapter(moviesAdapter);
           progressBar.setVisibility(View.GONE);
            moviesAdapter.notifyDataSetChanged();

        }



        @Override
        public void onFailure(Call<Movies> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            Log.d("Error: ", call.toString() + " " + t.getMessage() + " " + t.getCause());

        }
    }
}

