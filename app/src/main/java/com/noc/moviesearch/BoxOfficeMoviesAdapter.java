package com.noc.moviesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BoxOfficeMoviesAdapter extends ArrayAdapter<BoxOfficeMovie> {

    public BoxOfficeMoviesAdapter(Context context,List<BoxOfficeMovie> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoxOfficeMovie movie = getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_box_office_movie, parent, false);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvAverageVote);
        TextView tvYear = (TextView) convertView.findViewById(R.id.tvYear);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivPosterImage);

        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getVoteAverage());
        tvYear.setText(movie.getReleaseDate());
        Picasso.with(getContext()).load(movie.getPosterUrl()).resize(100, 100).placeholder(R.drawable.not_found).into(imageView);
        return convertView;
    }
}
