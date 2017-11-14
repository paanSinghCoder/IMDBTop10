package com.topten.grv.imdbtop10.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by grv on 11-11-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "movies_table";
    private static final String DATABASE_NAME = "movies.db";

    SQLiteDatabase db;

    private static final String ID = "ID";
    private static final String title = "title";
    private static final String year = "year";
    private static final String rating = "rating";
    private static final String thumb = "thumb";
    private static final String url = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
         db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, year TEXT, rating TEXT, thumb TEXT, url TEXT) ");
    }

    public void createTable(){
        //SQLiteDatabase db = this.getWritableDatabase();
        //String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("+ title + " TEXT , " + year + " TEXT," +
        //        " " + rating + " TEXT, " + thumb + " TEXT, " + url + " TEXT)";
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME  + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, year TEXT, rating TEXT, thumb TEXT, url TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //String upgradeTable = "DROP IF TABLE EXISTS " + TABLE_NAME;
        //db.execSQL(upgradeTable);
        //onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addAllData(String item1, String item2, String item3, String item4, String item5){
        createTable();
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title, item1);
        contentValues.put(year, item2);
        contentValues.put(rating, item3);
        contentValues.put(thumb, item4);
        contentValues.put(url, item5);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else
        {
            return true;
        }
    }

    public Cursor getListContents(){
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;

    }

    public Cursor searchRecord(String searchTerm)
    {
        db = this.getReadableDatabase();
        String query ="SELECT  *  FROM " + TABLE_NAME + " WHERE title  LIKE  '%" + searchTerm + "%' OR '" + searchTerm + "%' OR '%" + searchTerm + "'";
        //String q = "Select * from " + TABLE_NAME + " where title like '%" + replace(search_criteria, '%', '[%]') + '%'

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }

        return c;
    }

    public boolean deleteAll() {
        //SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        return true;
    }
}
