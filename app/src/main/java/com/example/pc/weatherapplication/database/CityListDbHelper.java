package com.example.pc.weatherapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pc.weatherapplication.CityList;

import java.util.ArrayList;
import java.util.List;

import static com.example.pc.weatherapplication.database.CityListContract.SQL_CREATE_ENTRIES;
import static com.example.pc.weatherapplication.database.CityListContract.SQL_DELETE_ENTRIES;

public class CityListDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WeatherDatabase.db";

    public CityListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertNewRow(CityList cityList)  {
        ContentValues values = new ContentValues();
        values.put(CityListContract.CityEntry.COLUMN_ID, cityList.getId());
        values.put(CityListContract.CityEntry.COLUMN_NAME, cityList.getNm());
        values.put(CityListContract.CityEntry.COLUMN_CODE, cityList.getCountryCode());
        values.put(CityListContract.CityEntry.COLUMN_LON, cityList.getLon());
        values.put(CityListContract.CityEntry.COLUMN_LAT, cityList.getLat());

        long newRowId = getWritableDatabase().insert(CityListContract.CityEntry.TABLE_NAME, null, values);
        //Log.v("VVV", "" +newRowId);
        return newRowId;

    }


    public List<CityList> searchForCity(String city)   {

        String[] projection = {
                CityListContract.CityEntry._ID,
                CityListContract.CityEntry.COLUMN_ID,
                CityListContract.CityEntry.COLUMN_NAME,
                CityListContract.CityEntry.COLUMN_LON,
                CityListContract.CityEntry.COLUMN_LAT,
                CityListContract.CityEntry.COLUMN_CODE,
                CityListContract.CityEntry.IS_FAVORITE


        };

        String selection = CityListContract.CityEntry.COLUMN_NAME+ " LIKE ?";
        String[] selectionArgs = { city + '%' };

        Cursor cursor = getReadableDatabase().query(
                CityListContract.CityEntry.TABLE_NAME,                      // The table to query
                projection,                                                 // The columns to return
                selection,                                                  // The columns for the WHERE clause
                selectionArgs,                                                       // The values for the WHERE clause
                null,                                                       // don't group the rows
                null,                                                       // don't filter by row groups
                null                                                        // The sort order
        );

        List<CityList> cityNamesList = new ArrayList<>();
        while(cursor.moveToNext()) {
            cityNamesList.add(new CityList(cursor));
        }

        if (cityNamesList.size() > 0) {

        } else {
            Log.v("VVV", "SQL is empty for this search");
        }
        cursor.close();
        return cityNamesList;
    }

    public void addToFavorites(String cityId)   {

        ContentValues values = new ContentValues();
        values.put(CityListContract.CityEntry.IS_FAVORITE, "true");
        getWritableDatabase().update(CityListContract.CityEntry.TABLE_NAME, values, CityListContract.CityEntry.COLUMN_ID + "= ?", new String[]{cityId});

        Log.v("VVV", cityId);
    }

    public void removeFromFavorites(String cityId)   {

        ContentValues values = new ContentValues();
        values.put(CityListContract.CityEntry.IS_FAVORITE, "false");
        getWritableDatabase().update(CityListContract.CityEntry.TABLE_NAME, values, CityListContract.CityEntry.COLUMN_ID + "= ?", new String[]{cityId});

        Log.v("VVV", cityId);
    }

    public List<CityList> searchForFavorites()   {

        String[] projection = {
                CityListContract.CityEntry._ID,
                CityListContract.CityEntry.COLUMN_ID,
                CityListContract.CityEntry.COLUMN_NAME,
                CityListContract.CityEntry.COLUMN_LON,
                CityListContract.CityEntry.COLUMN_LAT,
                CityListContract.CityEntry.COLUMN_CODE,
                CityListContract.CityEntry.IS_FAVORITE


        };

        String selection = CityListContract.CityEntry.IS_FAVORITE + "= ?";
        String[] selectionArgs = { "true" };

        Cursor cursor = getReadableDatabase().query(
                CityListContract.CityEntry.TABLE_NAME,                      // The table to query
                projection,                                                 // The columns to return
                selection,                                                  // The columns for the WHERE clause
                selectionArgs,                                              // The values for the WHERE clause
                null,                                                       // don't group the rows
                null,                                                       // don't filter by row groups
                null                                                        // The sort order
        );

        List<CityList> cityNamesList = new ArrayList<>();
        while(cursor.moveToNext()) {
            cityNamesList.add(new CityList(cursor));
        }

        if (cityNamesList.size() > 0) {

        } else {
            Log.v("VVV", "SQL is empty for this search");
        }
        cursor.close();
        return cityNamesList;
    }
}