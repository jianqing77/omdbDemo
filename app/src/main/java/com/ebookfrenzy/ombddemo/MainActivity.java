package com.ebookfrenzy.ombddemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebookfrenzy.ombddemo.Utils.CommonUtils;
import com.ebookfrenzy.ombddemo.databinding.ActivityMainBinding;
import com.ebookfrenzy.ombddemo.omdbApiRetrofitService.ResultWithDetail;
import com.ebookfrenzy.ombddemo.omdbApiRetrofitService.RetrofitLoader;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ResultWithDetail> {

    ActivityMainBinding binding;
    private EditText mSearchEditText;
    private String mMovieTitle;
    private ProgressBar mProgressBar;
    private Button mSearchButton;

    private MovieRecyclerViewAdapter mMovieAdapter;

    // TODO: check LOADER_ID meaning
    private static final int LOADER_ID = 1;

    private static final String LOG_TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.searchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    startSearch(view);
                    handled = true;
                }
                return handled;
            }
        });
        mMovieAdapter = new MovieRecyclerViewAdapter(this, null);
        binding.recyclerView.setAdapter(mMovieAdapter);
        // create a grid layout manager
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(R.integer.grid_column_count, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerView.setItemAnimator(null);
        // attach layout manager to the recycler view
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        LoaderManager.enableDebugLogging(true);
    }

    @NonNull
    @Override
    public Loader<ResultWithDetail> onCreateLoader(int id, @Nullable Bundle args) {
        return new RetrofitLoader(MainActivity.this, args.getString("movieTitle"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ResultWithDetail> loader, ResultWithDetail resultWithDetail) {
        binding.progressSpinner.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        if (resultWithDetail.getResponse().equals("True")) {
            mMovieAdapter.swapData(resultWithDetail.getMovieDetailList());
        } else {
            Snackbar.make(binding.recyclerView, 
                    getResources().getString(R.string.snackbar_title_not_found), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ResultWithDetail> loader) {
        mMovieAdapter.swapData(null);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("movie title", mMovieTitle);
        outState.putInt("progress_visibility", binding.progressSpinner.getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int progress_visibility = savedInstanceState.getInt("progress_visibility");
        if (progress_visibility == View.VISIBLE) {
            binding.progressSpinner.setVisibility(View.VISIBLE);
        }
        mMovieTitle = savedInstanceState.getString("movie title");
        if (mMovieTitle != null) {
            Bundle args = new Bundle();
            args.putString("movieTitle", mMovieTitle);
            LoaderManager.getInstance(this).initLoader(LOADER_ID, args, this);
        }
    }

    public void startSearch(View view) {
        // if the network is available
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {
            CommonUtils.hideSoftKeyboard(MainActivity.this);
            String movieTitle = mSearchEditText.getText().toString().trim();
            if (!movieTitle.isEmpty()) {
                Bundle args = new Bundle();
                args.putString("movieTitle", movieTitle);
                LoaderManager.getInstance(this).restartLoader(LOADER_ID, args, this);
                mMovieTitle = movieTitle;
                mProgressBar.setVisibility(View.VISIBLE); // TODO: check meaning
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                Snackbar.make(binding.recyclerView,
                        "Please enter a movie title",
                        Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(binding.recyclerView,
                    "Network is not available! Please connect to the internet",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}