package com.example.pc.weatherapplication.database;


import android.provider.BaseColumns;
import android.transition.CircularPropagation;

public final class CityListContract {

    private CityListContract() {


    }

    public static class CityEntry implements BaseColumns {
        public static final String TABLE_NAME = "CityList";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "nm";
        public static final String COLUMN_LON = "lon";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_CODE = "countryCode";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CityEntry.TABLE_NAME + " (" +
                    CityEntry._ID + " INTEGER PRIMARY KEY," +
                    CityEntry.COLUMN_ID + " TEXT," +
                    CityEntry.COLUMN_NAME + " TEXT," +
                    CityEntry.COLUMN_LON + " TEXT," +
                    CityEntry.COLUMN_LAT + " TEXT," +
                    CityEntry.COLUMN_CODE + " TEXT" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CityEntry.TABLE_NAME;
}
