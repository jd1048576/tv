package jdr.tvtracker.data.remote;

import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.data.remote.entities.SeasonsResponse;
import jdr.tvtracker.data.remote.entities.TVResultsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbInterface {
    @GET("discover/tv?sort_by=vote_average.desc&vote_count.gte=30&with_original_language=en&include_null_first_air_dates=false&without_genres=99|10763|10764|10766|10767")
    Call<TVResultsResponse> getDiscoverTVTopRated(@Query("page") int i);

    @GET("discover/tv?sort_by=popularity.desc&with_original_language=en&include_null_first_air_dates=false&without_genres=99|10763|10764|10766|10767")
    Call<TVResultsResponse> getDiscoverTVTrending(@Query("page") int i);

    @GET("discover/tv?sort_by=popularity.desc&with_original_language=en&include_null_first_air_dates=false&without_genres=99|10763|10764|10766|10767")
    Call<TVResultsResponse> getDiscoverTVUpcoming(@Query("page") int i, @Query("first_air_date.gte") String str);

    @GET("tv/{tv_id}")
    Call<SeasonsResponse> getSeasons(@Path("tv_id") int i, @Query("append_to_response") String str);

    @GET("tv/{tv_id}?append_to_response=credits,content_ratings,external_ids,recommendations,similar,videos")
    Call<Show> getShow(@Path("tv_id") int i);

    @GET("search/tv")
    Call<TVResultsResponse> getTVSearch(@Query("query") String str, @Query("page") int i);
}
