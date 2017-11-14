package com.topten.grv.imdbtop10;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.topten.grv.imdbtop10.adapter.CustomSearchListAdapter;
import com.topten.grv.imdbtop10.adapter.DatabaseHelper;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    DatabaseHelper myDB;
    ListView searchListView;
    //TextView textViewMessage;

    Cursor data;

    ArrayList<String> arrayListTitle = new ArrayList<>();
    ArrayList<String> arrayListYear = new ArrayList<>();
    ArrayList<String> arrayListRating = new ArrayList<>();
    ArrayList<String> arrayListThumb = new ArrayList<>();
    ArrayList<String> arrayListUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myDB = new DatabaseHelper(SearchActivity.this);
        searchListView = (ListView) findViewById(R.id.searchListView);
        //textViewMessage = (TextView) findViewById(R.id.textViewMessage);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        searchView = findViewById(R.id.searchBar);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                myDB = new DatabaseHelper(SearchActivity.this);

                data = myDB.searchRecord(query);//Sends search term to database helper which returns all data found according to the search term

                if (data==null){
                    //textViewMessage.setVisibility(View.VISIBLE);
                    Toasty.error(SearchActivity.this, "Not found", Toast.LENGTH_SHORT, true);
                }else {
                    //clearing arrayList to stop appending data to listview after every search is made>>>START
                    arrayListTitle.clear();
                    arrayListYear.clear();
                    arrayListRating.clear();
                    arrayListThumb.clear();
                    arrayListUrl.clear();
                    //clearing arrayList to stop appending data to listview after every search is made>>>END

                    searchResultMethod();//Calling method in this class, present below
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //this gets called when we start typing in searchview
                return false;
            }

            private void searchResultMethod() {
                if(data.getCount() == 0)
                {
                    Toast toastem = Toasty.warning(SearchActivity.this, "Database is empty", Toast.LENGTH_SHORT, true);
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



                CustomSearchListAdapter customSearchListAdapter = new CustomSearchListAdapter(SearchActivity.this,arrayListTitle, arrayListYear, arrayListRating, arrayListThumb, arrayListUrl);// it was getContext() earlier



                searchListView.setAdapter(customSearchListAdapter);
                searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent webActivityIntent = new Intent(SearchActivity.this, WebViewActivity.class);
                        webActivityIntent.putExtra("url_list",arrayListUrl);//Sending imdb url to web view activity
                        webActivityIntent.putExtra("position",i);
                        startActivity(webActivityIntent);

                    }
                });



            }
        });


    }
}


