package com.ebookfrenzy.ombddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ebookfrenzy.ombddemo.databinding.ActivityDetailBinding;
import com.ebookfrenzy.ombddemo.omdbApiRetrofitService.Detail;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL = "movie_detail";
    public static final String IMAGE_URL = "image_url";
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final Detail detail = getIntent().getParcelableExtra(MOVIE_DETAIL);
        final String imageUrl =  getIntent().getStringExtra(IMAGE_URL);
        Glide.with(this).load(imageUrl).into(binding.mainBackdrop);

        // set title for the appbar
        binding.mainCollapsing.setTitle(detail.Title);
        // settle the other details
        binding.gridTitle.setText(detail.Title);
        binding.gridWriters.setText(detail.Writer);
        binding.gridActors.setText(detail.Actors);
        binding.gridDirector.setText(detail.Director);
        binding.gridGenre.setText(detail.Genre);
        binding.gridReleased.setText(detail.Released);
        binding.gridPlot.setText(detail.Plot);
        binding.gridRuntime.setText(detail.Runtime);
    }

}
