package com.noc.moviesearch;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.EasyPermissions;

public class BoxOfficeActivity extends AppCompatActivity {

    TMDBClient client;
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    private ProgressBar progressBar;
    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        setupMovieSelectedListener();
        String[] perms = {android.Manifest.permission.INTERNET};
        if(EasyPermissions.hasPermissions(this, perms)) {
            fetchBoxOfficeMovies("");
        }
        else {
            EasyPermissions.requestPermissions(this, "Internet", 74, perms);
        }
    }

    private void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(BoxOfficeActivity.this, BoxOfficeDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

    public void fetchBoxOfficeMovies(String query) {
        progressBar.setVisibility(View.VISIBLE);
        client = new TMDBClient();
        if(query == "") {
            adapterMovies.clear();
            adapterMovies.notifyDataSetChanged();
            client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    JSONArray items;
                    try {
                        items = response.getJSONArray("results");
                        ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                        for (BoxOfficeMovie movie : movies) {
                            adapterMovies.add(movie);
                        }
                        adapterMovies.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        else {
            adapterMovies.clear();
            adapterMovies.notifyDataSetChanged();
            client.searchBoxOfficeMovies(query, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    JSONArray items;
                    try {
                        items = response.getJSONArray("results");
                        ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                        for (BoxOfficeMovie movie : movies) {
                            adapterMovies.add(movie);
                        }
                        adapterMovies.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_box_office, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBoxOfficeMovies(query);
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                BoxOfficeActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}












