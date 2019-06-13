package com.apps.testprojek.data.remote;

import com.apps.testprojek.data.remote.response.FilmDetailResponse;
import com.apps.testprojek.data.remote.response.FilmResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RemoteService {

    //http://www.omdbapi.com/?apikey=20bd772c&s=avengers&page=2
    @GET("/")
    Call<FilmResponse> getFilmOMDB(@Query("apikey") String apikey,
                                @Query("s") String s,
                                @Query("type") String type,
                                @Query("y") String y,
                                @Query("page") String page);

    //http://www.omdbapi.com/?apikey=20bd772c&t=Avengers Assemble&plot=full
    @GET("/")
    Call<FilmDetailResponse> getFilmDetailOMDB(@Query("apikey") String apikey,
                                               @Query("t") String t,
                                               @Query("plot") String plot);
}
