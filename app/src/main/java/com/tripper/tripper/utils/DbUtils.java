package com.tripper.tripper.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.tripper.tripper.services.myContentProvider;
import com.tripper.tripper.core.Trip;

public class DbUtils {

    public static int addNewTrip(Activity activity, Trip newTrip){
        ContentValues contentValues = newTrip.tripToContentValues();
        Uri uri = activity.getContentResolver().insert(myContentProvider.CONTENT_TRIPS_URI, contentValues);
        return Integer.parseInt(uri.getPathSegments().get(myContentProvider.TRIPS_ID_PATH_POSITION));
    }

    public static Trip getLastTrip(Context context){
        Trip lastTrip = null;
        Cursor cursor = context.getContentResolver().query(myContentProvider.CONTENT_TRIPS_URI, null, null,
                null, " LIMIT 1");
        if(cursor != null && cursor.moveToFirst()) {
            lastTrip = new Trip(cursor);
            cursor.close();
        }
        return lastTrip;
    }

    public static String getWhereClause(String[] columns) {
        String whereClause = "";

        for (String col : columns) {
            if (!whereClause.isEmpty()) {
                whereClause += " OR ";
            }

            whereClause += col + " like ? ";
        }

        return whereClause;
    }
}
