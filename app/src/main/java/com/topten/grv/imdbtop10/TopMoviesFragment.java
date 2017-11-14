package com.topten.grv.imdbtop10;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.topten.grv.imdbtop10.adapter.CustomListAdapter;
import com.topten.grv.imdbtop10.adapter.DatabaseHelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static java.security.AccessController.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopMoviesFragment extends Fragment {

    private DatabaseHelper myDB;
    private View view = null;
    private Cursor data;

    ListView listView;

    ArrayList<String> arrayListTitle = new ArrayList<>();
    ArrayList<String> arrayListYear = new ArrayList<>();
    ArrayList<String> arrayListRating = new ArrayList<>();
    ArrayList<String> arrayListThumb = new ArrayList<>();
    ArrayList<String> arrayListUrl = new ArrayList<>();

    //private Context context;
    public TopMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_top_movies, container, false);

        myDB = new DatabaseHelper(getActivity());
        listView = (ListView) view.findViewById(R.id.listView);
        //searchListView = (ListView) view.findViewById(R.id.searchListView);


        data = myDB.getListContents();




        if(data.getCount() == 0)
        {
            Toast toastem = Toasty.warning(getActivity(), "Database is empty", Toast.LENGTH_SHORT, true);
            toastem.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
            toastem.show();
        }else
        {
            while(data.moveToNext())
            {
                //String s = new String(data.getString(0)).toString();

                arrayListTitle.add(data.getString(1));//1 is the Column number in database. 0 is the id so we put 1 which is the movie title
                arrayListYear.add(data.getString(2));

                //Toast.makeText(getActivity(), data.getString(1), Toast.LENGTH_SHORT).show();
                arrayListRating.add(data.getString(3));
                arrayListThumb.add(data.getString(4));
                //Toast.makeText(getActivity(), arrayListThumb.get(i), Toast.LENGTH_SHORT).show();

                arrayListUrl.add(data.getString(5));
                //Toast.makeText(getActivity(), arrayListUrl.get(i), Toast.LENGTH_SHORT).show();
                //i++;

            }
        }



        CustomListAdapter customListAdapter = new CustomListAdapter(this.getActivity(),arrayListTitle, arrayListYear, arrayListRating, arrayListThumb, arrayListUrl);// it was getContext() earlier
        listView.setAdapter(customListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent webActivityIntent = new Intent(getActivity(), WebViewActivity.class);
                webActivityIntent.putExtra("url_list",arrayListUrl);//Sending imdb url to web view activity
                webActivityIntent.putExtra("position",i);
                startActivity(webActivityIntent);

            }
        });



        return view;
    }

}


