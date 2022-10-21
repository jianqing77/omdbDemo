package com.ebookfrenzy.ombddemo.omdbApiRetrofitService;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class searchService {
    private static final String API_URL = "http://www.omdbapi.com";
    private static OmdbAPI sOmdbAPI;
    private static final String API_KEY = "ecafb541";

    private static void setsOmdbAPI() {
        if (sOmdbAPI == null) {
            // Create a REST adapter which points the Omdb API.
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apikey", API_KEY)
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Create an instance of our OMDB API interface.
            sOmdbAPI = retrofit.create(OmdbAPI.class);
        }
    }

    public static Result performSearch(String title) throws IOException {
        setsOmdbAPI();

        // Create a call instance for looking up the movie names by title.
        Call<Result> call = sOmdbAPI.Result(title);

        return call.execute().body();
    }

    public static Detail getDetail(String imdbId) throws IOException {
        setsOmdbAPI();

        Call<Detail> call = sOmdbAPI.Detail(imdbId);

        return call.execute().body();
    }
}
