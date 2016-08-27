package com.bus.bdtravelroute.mhraju.travelroute.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bus.bdtravelroute.mhraju.travelroute.Route;

import java.util.ArrayList;

/**
 * Created by supto on 7/16/2016.
 */
public class RouteManager {

    private Route route;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public RouteManager(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = databaseHelper.getWritableDatabase();
    }

    private void close() {
        databaseHelper.close();
    }


    public boolean addRoute(Route route) {
        this.open();
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.COL_VEHICLE_TYPE, route.getVehicleType());
        cv.put(DatabaseHelper.COL_STARTING_POINT, route.getStartingPoint());
        cv.put(DatabaseHelper.COL_ENDING_POINT, route.getEndingPoint());
        cv.put(DatabaseHelper.COL_TICKET_PRICE, route.getTicketPrice());
        cv.put(DatabaseHelper.COL_VIA, route.getVia());
        cv.put(DatabaseHelper.COL_COMMENT, route.getComment());
        cv.put(DatabaseHelper.COL_USERNAME, route.getUserName());

        long inserted = database.insert(DatabaseHelper.TABLE_NAME, null, cv);
        this.close();

        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Route getRoute(int id) {

        this.open();
        Route route;
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null,
                DatabaseHelper.COL_ID + "= " + id, null, null, null, null);

        cursor.moveToFirst();
        String vehicleType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VEHICLE_TYPE));
        String startingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STARTING_POINT));
        String endingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ENDING_POINT));
        String ticketPrice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TICKET_PRICE));
        String via = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VIA));
        String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT));
        String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));

        route = new Route(vehicleType, startingPoint, endingPoint, ticketPrice, via, comment, userName);
        route.setId(id);
        this.close();
        return route;
    }

    public ArrayList<Route> getAllRoute() {
        this.open();
        ArrayList<Route> routesList = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String vehicleType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VEHICLE_TYPE));
                String startingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STARTING_POINT));
                String endingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ENDING_POINT));
                String ticketPrice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TICKET_PRICE));
                String via = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VIA));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));

                Route route = new Route(vehicleType, startingPoint, endingPoint, ticketPrice, via, comment, userName);
                route.setId(id);
                routesList.add(route);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return routesList;
    }


    public ArrayList<Route> getUserRoutes(String loggedInUserName) {
        this.open();
        ArrayList<Route> routesList = new ArrayList<>();


        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null,

                DatabaseHelper.COL_USERNAME + " = " + "'" + loggedInUserName + "'", null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String vehicleType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VEHICLE_TYPE));
                String startingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STARTING_POINT));
                String endingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ENDING_POINT));
                String ticketPrice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TICKET_PRICE));
                String via = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VIA));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));

                Route route = new Route(vehicleType, startingPoint, endingPoint, ticketPrice, via, comment, userName);
                route.setId(id);
                routesList.add(route);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return routesList;
    }

    public boolean deleteRoute(Route route) {

        this.open();
        int deleted = database.delete(DatabaseHelper.TABLE_NAME,
                DatabaseHelper.COL_ID + " = " + route.getId(), null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }

    public boolean updateRoute(Route route) {

        this.open();
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.COL_VEHICLE_TYPE, route.getVehicleType());
        cv.put(DatabaseHelper.COL_STARTING_POINT, route.getStartingPoint());
        cv.put(DatabaseHelper.COL_ENDING_POINT, route.getEndingPoint());
        cv.put(DatabaseHelper.COL_TICKET_PRICE, route.getTicketPrice());
        cv.put(DatabaseHelper.COL_VIA, route.getVia());
        cv.put(DatabaseHelper.COL_COMMENT, route.getComment());
        cv.put(DatabaseHelper.COL_USERNAME, route.getUserName());

        int updated = database.update(DatabaseHelper.TABLE_NAME, cv,
                DatabaseHelper.COL_ID + " = " + route.getId(), null);

        this.close();

        if (updated > 0) {
            return true;
        } else
            return false;

    }

    public ArrayList<Route> searchRoute(String from, String destination) {

        this.open();
        ArrayList<Route> routesList = new ArrayList<>();


        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null,

                DatabaseHelper.COL_STARTING_POINT + " = '" + from + "' AND " +
                        DatabaseHelper.COL_ENDING_POINT + " = '" + destination + "'",
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String vehicleType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VEHICLE_TYPE));
                String startingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STARTING_POINT));
                String endingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ENDING_POINT));
                String ticketPrice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TICKET_PRICE));
                String via = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VIA));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));

                Route route = new Route(vehicleType, startingPoint, endingPoint, ticketPrice, via, comment, userName);
                route.setId(id);
                routesList.add(route);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return routesList;

    }

    public ArrayList<Route> searchRoute(String vehicle, String from, String destination) {

        this.open();
        ArrayList<Route> routesList = new ArrayList<>();


        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, null,

                DatabaseHelper.COL_STARTING_POINT + " = '" + from + "' AND " +
                        DatabaseHelper.COL_ENDING_POINT + " = '" + destination + "' AND " +
                        DatabaseHelper.COL_VEHICLE_TYPE + " = '" + vehicle + "'",
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String vehicleType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VEHICLE_TYPE));
                String startingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_STARTING_POINT));
                String endingPoint = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ENDING_POINT));
                String ticketPrice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TICKET_PRICE));
                String via = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_VIA));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME));

                Route route = new Route(vehicleType, startingPoint, endingPoint, ticketPrice, via, comment, userName);
                route.setId(id);
                routesList.add(route);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return routesList;

    }
}
