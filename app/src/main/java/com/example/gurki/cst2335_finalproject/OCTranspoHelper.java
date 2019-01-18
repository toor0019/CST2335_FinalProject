package com.example.gurki.cst2335_finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gurki on 2018-11-19.
 */

public class OCTranspoHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DataBase_NAME = "octranspo15.db";
    public OCTranspoHelper(Context mContext) {
        super(mContext, DataBase_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + OCTranspoSchema.Stops.NAME + "( " +
                OCTranspoSchema.Stops.Cols.STOP_NUMBER + " TEXT , " +
                OCTranspoSchema.Stops.Cols.RouteID + " TEXT, " +
                OCTranspoSchema.Stops.Cols.RouteName +" TEXT "+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if Exists " + OCTranspoSchema.Stops.NAME);
        onCreate(db);
    }


}
