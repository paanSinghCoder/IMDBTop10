package com.topten.grv.imdbtop10;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.topten.grv.imdbtop10.adapter.DatabaseHelper;
import com.topten.grv.imdbtop10.pojo.Movie;
import com.topten.grv.imdbtop10.pojo.Poster;
import com.topten.grv.imdbtop10.pojo.Url;

import es.dmoral.toasty.Toasty;

public class StartActivity extends AppCompatActivity {

    Button btn, btn_delete;
    SharedPreferences sharedPreferences;

    private static final String URL_BY_ID = "https://www.theimdbapi.org/api/movie?movie_id=";
    private static final String[] ID_TOP_LIST = {"tt0111161","tt0068646","tt0468569","tt0071562","tt0110912",
            "tt0167260","tt0108052","tt0050083","tt0060196","tt0120737"};
    private String jsonResponse, title, year, rating, thumb, url;


    int count = 1;

    RequestQueue queue;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make fullscreen>>>>START
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Make fullscreen>>>>END

        setContentView(R.layout.activity_start);
        btn = (Button) findViewById(R.id.start_reg_btn);

        //Sharedpref to check if the user has already fetched the json>>START
        //sharedPreferences = getSharedPreferences("userVisitCount", Context.MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("userVisitCount", "2");

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Sharedpref to check if the user has already fetched the json>>END


        //JSON fetch>>>START
        mDatabaseHelper = new DatabaseHelper(this);


        if (!prefs.getBoolean("firstTime", false))
        {

            for (int i = 0; i < 10; i++) {
                final StringRequest request = new StringRequest(URL_BY_ID + ID_TOP_LIST[i], new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonResponse = response;
                        //GSON>>START
                        Gson gson = new Gson();
                        Movie movie = gson.fromJson(jsonResponse, Movie.class);
                        Poster poster = movie.poster;
                        Url mUrl = movie.url;
                        //Toast.makeText(MainActivity.this, jsonResponse, Toast.LENGTH_SHORT).show();

                        title = movie.getTitle();
                        year = movie.getYear();
                        rating = movie.getRating();
                        thumb = poster.getThumb();
                        url = mUrl.getUrl();

                        if (title == null || title == "")
                            title = "The Avengers";
                        if (year == null || year == "")
                            year = "2012";
                        if (rating == null || rating == "")
                            rating = "8.1";
                        if (thumb == null || thumb == "")
                            thumb = "https://images-na.ssl-images-amazon.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_UX182_CR0,0,182,268_AL_.jpg";//In case the restapi sends null, this default poster placeholder will be set
                        if (url == null || url == "")
                            url = "http://www.imdb.com/title/tt0848228/";


                        addData(title, year, rating, thumb, url);


                        // Toast.makeText(StartActivity.this, mUrl.getUrl(), Toast.LENGTH_SHORT).show();

                        //Log.d("debug", "firstname : " + poster.getThumb());
                        //GSON>>>END
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast toastba = Toasty.error(StartActivity.this, "Bad response from server", Toast.LENGTH_SHORT, true);
                        toastba.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                        toastba.show();
                        if (title == null || title == "")
                            title = "The Avengers";
                        if (year == null || year == "")
                            year = "2012";
                        if (rating == null || rating == "")
                            rating = "8.1";
                        if (thumb == null || thumb == "")
                            thumb = "https://images-na.ssl-images-amazon.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_UX182_CR0,0,182,268_AL_.jpg";//In case the restapi sends null, this default poster placeholder will be set
                        if (url == null || url == "")
                            url = "http://www.imdb.com/title/tt0848228/";





                        addData(title, year, rating, thumb, url);
                       // count++;
                    }
                });

                queue = Volley.newRequestQueue(this);
                queue.add(request);
            }

            btn.setEnabled(false);
            btn.setBackgroundColor(getResources().getColor(R.color.btn_disabled));
            btn.setText("Fetching Json...");


            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }else {

            Toast toastda = Toasty.success(StartActivity.this, "Data found in database", Toast.LENGTH_SHORT, true);
            toastda.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
            toastda.show();
            btn.setEnabled(true);
            Drawable d = getResources().getDrawable(R.drawable.button_startactivity_style);
            btn.setBackgroundDrawable(d);
            btn.setText("GET STARTED");


        }


        //JSON fetch>>>END




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        });

        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setMessage("Are you sure? This will delete the local database and you will have to restart the app " +
                        "in order to fetch data from server again. Hit DELETE to delete the database.")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDatabaseHelper.deleteAll();
                                Toast toastta = Toasty.info(StartActivity.this, "Database deleted", Toast.LENGTH_SHORT, true);
                                toastta.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                                toastta.show();
                                btn.setEnabled(false);
                                btn.setText("Restart App");
                                btn.setBackgroundColor(getResources().getColor(R.color.btn_disabled));
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("firstTime", false);
                                editor.commit();
                            }
                        }).setNegativeButton("CANCEL", null);
                AlertDialog alert = builder.create();
                alert.show();



            }
        });

        setVideoBackground();
    }

    private void addData(String title, String year, String rating, String thumb, String url) {


        boolean insertData = mDatabaseHelper.addAllData(title, year, rating, thumb, url);//calling the addAllData in DatabaseHelper class

        if(insertData) {

            count++;

            if(count>9){

                Toast toastsuc = Toasty.success(StartActivity.this, "Json inserted in database successfully", Toast.LENGTH_SHORT, true);
                toastsuc.setGravity(Gravity.TOP|Gravity.CENTER, 2, 0);
                toastsuc.show();
                btn.setEnabled(true);
                Drawable d = getResources().getDrawable(R.drawable.button_startactivity_style);
                btn.setBackgroundDrawable(d);
                btn.setText("GET STARTED");
            }

        }
        else
          Toasty.error(StartActivity.this, "Database insertion failed", Toast.LENGTH_SHORT, true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setVideoBackground();
    }

    private void setVideoBackground() {
        final VideoView view = (VideoView)findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video_background;
        view.setVideoURI(Uri.parse(path));
        view.start();
        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                view.start();
            }
        });
    }
}
