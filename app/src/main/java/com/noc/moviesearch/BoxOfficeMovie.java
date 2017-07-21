package com.noc.moviesearch;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class BoxOfficeMovie implements Serializable {

    private static long serialVersionUID = -3710545724505928349L;

    private int id;
    private String title;
    private String releaseDate;
    private String synopsis;
    private String posterUrl;
    private int voteAverage;
    private ArrayList<String> cast;
    private ArrayList<String> genres;

    private int popularity;

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public String getCast() {
        return TextUtils.join("\n", cast);
    }

    public String getGenres() {
        return TextUtils.join(", ", genres);
    }

    public int getPopularity() {
        return popularity;
    }

    public static BoxOfficeMovie fromJson(JSONObject jsonObject) {
        BoxOfficeMovie b = new BoxOfficeMovie();
        try {
            b.id = jsonObject.getInt("id");
            b.title = jsonObject.getString("title");
            b.releaseDate = jsonObject.has("release_date")?jsonObject.getString("release_date"):"";
            b.synopsis = jsonObject.getString("overview");
            b.posterUrl = "https://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path");
            b.voteAverage = jsonObject.getInt("vote_average");
            b.popularity = jsonObject.getInt("popularity");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    public static BoxOfficeMovie fromJSONCast(JSONObject jsonObject) {
        BoxOfficeMovie b = new BoxOfficeMovie();
        try {
            b.cast = new ArrayList<>();
            JSONArray cast = jsonObject.has("cast")?jsonObject.getJSONArray("cast"):null;
            if(cast != null) {
                for (int i = 0; i < 10; i++) {
                    b.cast.add(cast.getJSONObject(i).getString("name") + " : " + cast.getJSONObject(i).getString("character"));
                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    public static BoxOfficeMovie fromJSONGenres(JSONObject jsonObject) {
        BoxOfficeMovie b = new BoxOfficeMovie();
        try {
            b.genres = new ArrayList<>();
            JSONArray genres = jsonObject.getJSONArray("genres");
            for(int i = 0; i < genres.length(); i++) {
                b.genres.add(genres.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    public static ArrayList<BoxOfficeMovie> fromJson(JSONArray jsonArray) {
        ArrayList<BoxOfficeMovie> movies = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            BoxOfficeMovie movie = BoxOfficeMovie.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }
}