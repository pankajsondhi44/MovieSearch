package com.noc.moviesearch;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TMDBClient {
    private final String API_KEY = "8920df4824e698b704bc12fba56029a6";
    private final String API_DISCOVER_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=8920df4824e698b704bc12fba56029a6&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=1";
    private final String API_BASE_URL = "https://api.themoviedb.org/3/search/movie?api_key=8920df4824e698b704bc12fba56029a6&language=en-US";
    private AsyncHttpClient client;
    public int page = 1;
    public boolean adult = true;

    public TMDBClient() {
        this.client = new AsyncHttpClient();
    }

    public void getBoxOfficeMovies(JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(API_DISCOVER_URL, params, handler);
    }

    public void searchBoxOfficeMovies(String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("&query=" + query + "&page=" + page + "&include_adult=" + adult);
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    public void getMovieCredits(int id, JsonHttpResponseHandler handler) {
        String url = "https://api.themoviedb.org/3/movie/" + String.valueOf(id) + "/credits?api_key=8920df4824e698b704bc12fba56029a6";
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    public void getMovieDetails(int id, JsonHttpResponseHandler handler) {
        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=8920df4824e698b704bc12fba56029a6&language=en-US";
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}