package com.sisteminformasi.gigtrack;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeezerService {
    @GET("search")
    Call<SearchResponse> searchSongs(@Query("q") String query);
}
