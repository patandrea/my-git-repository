/*******************************************************
 * Name:        BoxOfficeAdapter
 * Description: Responsible for Mapping each BoxOfficeMovie
 *              to a particular view layout.
 *********************************************************/
package com.andrea.rottentomatosdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BoxOfficeAdapter extends ArrayAdapter<BoxOfficeMovie> {
    public BoxOfficeAdapter(Context context, ArrayList<BoxOfficeMovie> aMovies) {
        super(context, 0, aMovies);
    }
    public static final String TAG = "BoxOfficeAdapter";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: Complete the definition of the view for each movie

        BoxOfficeMovie movie = getItem(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_box_office, parent, false);
        }
        // Lookup Views
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast= (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);

        // Populate views
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText(movie.getCriticsScore());
        tvCast.setText(movie.getCastList());
        Picasso.with(getContext()).load(movie.getPosterUrl()).into(ivPosterImage);


        return convertView;
    }
}