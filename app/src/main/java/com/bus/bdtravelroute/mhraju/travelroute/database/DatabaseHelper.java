package com.bus.bdtravelroute.mhraju.travelroute.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by supto on 7/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "travel_easy";
    public static final int DATAVBASE_VERSION = 1;
    public static final String TABLE_NAME = "routes";

    public static final String COL_ID = "id";
    public static final String COL_VEHICLE_TYPE = "vehicle_type";
    public static final String COL_STARTING_POINT = "starting_point";
    public static final String COL_ENDING_POINT = "ending_point";
    public static final String COL_TICKET_PRICE = "ticket_price";
    public static final String COL_VIA= "via";
    public static final String COL_COMMENT = "comment";
    public static final String COL_USERNAME = "username";


    public static final String USER_TABLE = "users";
    public static final String USER_TABLE_COL_ID = "id";
    public static final String USER_TABLE_COL_USERNAME = "user_name";
    public static final String USER_TABLE_PASSWORD = "password";

    private static final String CREATE_ROUTE_TABLE=" CREATE TABLE "+ TABLE_NAME +
            "( "+ COL_ID +" INTEGER PRIMARY KEY, "+ COL_VEHICLE_TYPE +" TEXT," + COL_STARTING_POINT
            +" TEXT," + COL_ENDING_POINT +" TEXT," + COL_TICKET_PRICE +" TEXT," + COL_VIA
            +" TEXT," + COL_COMMENT+" TEXT," + COL_USERNAME
            +" TEXT )";

    private static final String CREATE_USERS_TABLE=" CREATE TABLE "+ USER_TABLE +
            "( "+ USER_TABLE_COL_ID +" INTEGER PRIMARY KEY, "+ USER_TABLE_COL_USERNAME +" TEXT," + USER_TABLE_PASSWORD
            +" TEXT )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATAVBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ROUTE_TABLE);
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);

        Log.d("Supto","route");
        Log.d("Supto","user");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
