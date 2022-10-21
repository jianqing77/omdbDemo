package com.ebookfrenzy.ombddemo.omdbApiRetrofitService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbAPI {
    @GET("?type=movie")
    Call<Result> Result(@Query("s") String Title);

    @GET("?plot=full")
    Call<Detail> Detail(@Query("i") String ImdbId);
}
