package com.noc.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BoxOfficeDetailActivity extends AppCompatActivity {

    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private TextView castTitle;
    private TextView tvGenres;

    private TMDBClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_detail);

        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvAudienceScore = (TextView) findViewById(R.id.tvPopularity);
        tvCriticsScore = (TextView) findViewById(R.id.tvAverageVote);
        castTitle = (TextView) findViewById(R.id.castTitle);
        tvGenres = (TextView) findViewById(R.id.tvGenres);

        BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    public void loadMovie(BoxOfficeMovie movie) {
        Toast.makeText(getApplicationContext(), R.string.loading_detail_message,Toast.LENGTH_SHORT).show();
        client = new TMDBClient();
        client.getMovieDetails(movie.getId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                BoxOfficeMovie b = BoxOfficeMovie.fromJSONGenres(response);
                if(b != null) {
                    tvGenres.setVisibility(View.VISIBLE);
                    tvGenres.setText(Html.fromHtml("<b>Genres : </b>" + b.getGenres()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        client.getMovieCredits(movie.getId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                BoxOfficeMovie Movie = BoxOfficeMovie.fromJSONCast(response);
                if(Movie != null) {
                    castTitle.setVisibility(View.VISIBLE);
                    tvCast.setVisibility(View.VISIBLE);
                    tvCast.setText(Movie.getCast());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        tvTitle.setText(movie.getTitle());
        tvSynopsis.setText(movie.getSynopsis());
        tvAudienceScore.setText(Html.fromHtml("<b>Average Vote : </b>" + movie.getVoteAverage() + "/10"));
        tvCriticsScore.setText(Html.fromHtml("<b>Popularity : </b>" + movie.getPopularity()));
        Picasso.with(this).load(movie.getPosterUrl()).placeholder(R.drawable.not_found_large).into(ivPosterImage);
    }
}





