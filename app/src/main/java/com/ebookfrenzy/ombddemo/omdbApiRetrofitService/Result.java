package com.ebookfrenzy.ombddemo.omdbApiRetrofitService;

import java.util.List;

public class Result {
    public List<Movie> Search;
    public String totalResults;
    public String Response;

    @Override
    public String toString() {
        return "Result{" +
                "Search=" + Search +
                ", totalResults='" + totalResults + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }

}
