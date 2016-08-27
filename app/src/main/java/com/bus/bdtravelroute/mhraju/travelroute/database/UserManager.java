package com.bus.bdtravelroute.mhraju.travelroute.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bus.bdtravelroute.mhraju.travelroute.User;
import com.bus.bdtravelroute.mhraju.travelroute.User;

/**
 * Created by supto on 7/17/2016.
 */
public class UserManager {

    private User user;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public UserManager(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = databaseHelper.getWritableDatabase();
    }

    private void close() {
        databaseHelper.close();
    }

    public boolean addUser(User user) {
        this.open();
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.USER_TABLE_COL_USERNAME, user.getUserName());
        cv.put(DatabaseHelper.USER_TABLE_PASSWORD, user.getPassword());

        long inserted = database.insert(DatabaseHelper.USER_TABLE, null, cv);
        this.close();

        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfUserExisted(User user){
        this.open();

        Cursor cursor = database.query(DatabaseHelper.USER_TABLE, null,
                DatabaseHelper.USER_TABLE_COL_USERNAME + " = " + "'" + user.getUserName() + "'", null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        }else{
            return true;
        }


    }

    public boolean checkIfUserExistedWithPassword(User user){
        this.open();

        Cursor cursor = database.query(DatabaseHelper.USER_TABLE, null,
                DatabaseHelper.USER_TABLE_COL_USERNAME + " = " + "'" + user.getUserName() + "' AND " + DatabaseHelper.USER_TABLE_PASSWORD + " = " + "'" + user.getPassword() + "'", null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        }else{
            return true;
        }
    }


}
