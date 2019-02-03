package com.hooq.demo.movie.movielist.presentation.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hooq.demo.movie.R;
import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.network.ResourceManager;

import java.util.List;

public class MovieAdapter extends BaseAdapter {

    private List<Movie> mItems;
    private final LayoutInflater mInflater;
    private Context context;


    public MovieAdapter(Context context, List<Movie> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ImageView picture;
        TextView name;

        if (view == null) {
            view = mInflater.inflate(R.layout.movie_item, parent, false);
            view.setTag(R.id.picture, view.findViewById(R.id.picture));
            view.setTag(R.id.text, view.findViewById(R.id.text));
        }


        Movie item = getItem(position);

        name = (TextView) view.getTag(R.id.text);
        name.setText(item.getTitle());

        picture = (ImageView) view.getTag(R.id.picture);
        Glide.with(context)
                .load(ResourceManager.getInstance().getImage(item.getPosterPath()))
                .placeholder(R.drawable.empty)
                .fitCenter()
                .into(picture);
        return view;
    }


    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public int getCount() {
        if (mItems != null)
            return mItems.size();
        else
            return 0;
    }

    @Override
    public Movie getItem(int i) {
        if (mItems != null)
            return mItems.get(i);
        else
            return null;
    }

    @Override
    public long getItemId(int i) {
        if (mItems != null)
            return mItems.get(i).getId();
        else
            return 0;

    }

}
