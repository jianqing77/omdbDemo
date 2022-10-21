package com.ebookfrenzy.ombddemo.omdbApiRetrofitService;

public class Movie {
    public String Title;
    public String Year;
    public String imdbID;
    public String Type;
    public String Poster;

    @Override
    public String toString() {
        return "\nMovie{" +
                "Title='" + Title + '\'' +
                ", Year='" + Year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", Type='" + Type + '\'' +
                ", Poster='" + Poster + '\'' +
                '}';
    }
}
