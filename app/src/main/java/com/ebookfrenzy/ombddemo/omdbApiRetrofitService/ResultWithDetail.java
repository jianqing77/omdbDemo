package com.ebookfrenzy.ombddemo.omdbApiRetrofitService;

import java.util.ArrayList;
import java.util.List;

public class ResultWithDetail {

    private List<Detail> movieDetailList;
    private String totalResults;
    private String Response;

    public ResultWithDetail(Result result) {
        this.totalResults = result.totalResults;
        this.Response = result.Response;
        movieDetailList = new ArrayList<>();
    }

    /**
     * Add a Detail Instance to the movieDetailList
     */
    public void addToList(Detail detail) {
        movieDetailList.add(detail);
    }

    /**
     * Get movie detail list.
     */
    public List<Detail> getMovieDetailList() {
        return this.movieDetailList;
    }

    /**
     * Get total Results
     * @return
     */
    public String getTotalResults() {
        return this.totalResults;
    }

    public String getResponse() {
        return this.Response;
    }
}
