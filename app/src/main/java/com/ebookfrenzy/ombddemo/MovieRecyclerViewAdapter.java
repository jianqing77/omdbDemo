package com.ebookfrenzy.ombddemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ebookfrenzy.ombddemo.databinding.ActivityMainBinding;
import com.ebookfrenzy.ombddemo.databinding.ListItemMovieBinding;
import com.ebookfrenzy.ombddemo.omdbApiRetrofitService.Detail;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    ListItemMovieBinding binding;
    private List<Detail> mValues;
    private Activity activity;


    public MovieRecyclerViewAdapter(Activity activity, List<Detail> items) {
        this.activity = activity;
        this.mValues = items;
    }

    /**
     * Class ViewHolder extended from RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ListItemMovieBinding itemBinding;

        public final View mView;
        public final TextView mTitleView;
        public final TextView mYearView;
        public final TextView mDirectorView;
        public final ImageView mThumbImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTitleView = (TextView) itemView.findViewById(R.id.movie_title);
            mYearView = (TextView) itemView.findViewById(R.id.movie_year);
            mThumbImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            mDirectorView = (TextView) itemView.findViewById(R.id.movie_director);
        }

    }


    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get movie details from the List with the position
        Detail detail = mValues.get(position);
        String title = detail.Title;
        String imdbID = detail.imdbID;
        String director = detail.Director;
        String year = detail.Year;
        // set text
        holder.mTitleView.setText(title);
        holder.mDirectorView.setText(director);
        holder.mYearView.setText(year);

        // Get image Url and Set poster image
        String imageUrl;
        if (!detail.Poster.equals("N/A")) {
            imageUrl = detail.Poster;
        } else {
            imageUrl = "https://bobcares.com/wp-content/uploads/2022/06/Cannot-find-type-microsoft.sqlserver.management.smo_.relocatefile.png";
        }
        // thumb image view
        holder.mThumbImageView.layout(0,0,0,0);
        Glide.with(this.activity.getApplicationContext()).load(imageUrl).into(holder.mThumbImageView);

        // set view
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(DetailActivity.MOVIE_DETAIL, detail);
                intent.putExtra(DetailActivity.IMAGE_URL, imageUrl);
                // add transition animation
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, holder.mThumbImageView, "poster");
                activity.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(this.activity.getApplicationContext()).clear(holder.mThumbImageView);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void swapData(List<Detail> items) {
        if (items != null) {
            mValues = items;
            notifyDataSetChanged();
        } else {
            mValues = null;
        }
    }
}
