package com.noc.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BoxOfficeDetailActivity extends AppCompatActivity {

    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private TextView tvCriticsConsensus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_detail);

        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvCriticsConsensus = (TextView) findViewById(R.id.tvCriticsConsensus);
        tvAudienceScore = (TextView) findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);

        BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    public void loadMovie(BoxOfficeMovie movie) {
        tvTitle.setText(movie.getTitle());
        tvSynopsis.setText(movie.getSynopsis());
        tvCast.setText(movie.getCast());
        tvAudienceScore.setText(Html.fromHtml("<b>Audience Score: </b>" + movie.getAudienceScore() + "%"));
        tvCriticsScore.setText(Html.fromHtml("<b>Critics Score: </b>" + movie.getCriticsScore() + "%"));
        tvCriticsConsensus.setText(Html.fromHtml("<b>Consensus:</b> " + movie.getCriticsConsensus()));
        Picasso.with(this).load(movie.getLargePosterUrl()).placeholder(R.drawable.not_found_large).into(ivPosterImage);
    }
}





