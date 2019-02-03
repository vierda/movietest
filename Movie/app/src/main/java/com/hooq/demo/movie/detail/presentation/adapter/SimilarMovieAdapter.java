package com.hooq.demo.movie.detail.presentation.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hooq.demo.movie.R;
import com.hooq.demo.movie.detail.data.entity.SimilarMovieEntity;
import com.hooq.demo.movie.detail.presentation.view.DetailMovieDataView;

import java.util.List;

public class SimilarMovieAdapter extends PagerAdapter {

    private Context context;
    private  List<SimilarMovieEntity> similarMovieEntities;
    private  LayoutInflater layoutInflater;
    private DetailMovieDataView dataView;


    public SimilarMovieAdapter(Context context, List<SimilarMovieEntity> similarMovieEntities, DetailMovieDataView dataView) {
        this.context = context;
        this.similarMovieEntities = similarMovieEntities;
        this.dataView = dataView;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setSimilarMovieEntities(List<SimilarMovieEntity> similarMovieEntities) {
        this.similarMovieEntities = similarMovieEntities;
    }

    @Override
    public int getCount() {
        return similarMovieEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.similar_movie_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        final SimilarMovieEntity entity = similarMovieEntities.get(position);

        Glide.with(context)
                .load(entity.getPosterPath())
                .placeholder(R.drawable.empty)
                .fitCenter()
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataView.onSimilarMovieClick(entity.getMovieId());
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
