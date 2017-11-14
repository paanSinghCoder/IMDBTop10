package com.topten.grv.imdbtop10.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.topten.grv.imdbtop10.R;

import java.util.ArrayList;

/**
 * Created by grv on 14-11-2017.
 */

public class CustomSearchListAdapter extends BaseAdapter {

    //TopMoviesFragment topMoviesFragment = new TopMoviesFragment();


    LayoutInflater inflater;
    Context c;
    ArrayList<String> arrayListTitle;
    ArrayList<String> arrayListYear;
    ArrayList<String> arrayListRating;
    ArrayList<String> arrayListThumb;
    ArrayList<String> arrayListUrl;


    public CustomSearchListAdapter(Context c, ArrayList<String> arrayListTitle, ArrayList<String> arrayListYear, ArrayList<String> arrayListRating,
                             ArrayList<String> arrayListThumb, ArrayList<String> arrayListUrl)
    {
        this.c = c;
        this.arrayListTitle = arrayListTitle;
        this.arrayListYear = arrayListYear;
        this.arrayListRating = arrayListRating;
        this.arrayListThumb = arrayListThumb;
        this.arrayListUrl = arrayListUrl;
    }

    @Override
    public int getCount() {
        return arrayListTitle.size();//Size is fixed to 10 as we are getting 10 movies from restapi
    }

    @Override
    public Object getItem(int i) {
        return arrayListTitle.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.single_cardview_layout,viewGroup,false);



        TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
        textTitle.setText(arrayListTitle.get(i));

        TextView textYear = (TextView) view.findViewById(R.id.textYear);
        textYear.setText(arrayListYear.get(i));

        TextView textRating = (TextView) view.findViewById(R.id.TextRating);
        textRating.setText(arrayListRating.get(i));

        TextView textBigRating = (TextView) view.findViewById(R.id.textBigRating);
        textBigRating.setText(arrayListRating.get(i));

        ImageView imagePoster = (ImageView) view.findViewById(R.id.imagePoster);
        Picasso.with(c).load(arrayListThumb.get(i)).into(imagePoster);



        return view;
    }
}
