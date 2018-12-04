package com.example.gurki.cst2335_finalproject.OC_Transpo.databse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gurki on 2018-11-19.
 */

public class OCTranspoHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DataBase_NAME = "octranspo.db";
    public OCTranspoHelper(Context mContext) {
        super(mContext, DataBase_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + OCTranspoSchema.Stops.NAME + "(" +
                " id_ integer primary key autoincrement, " +
                OCTranspoSchema.Stops.Cols.STOP_NUMBER + ", " +
                OCTranspoSchema.Stops.Cols.RouteID +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if Exists " + OCTranspoSchema.Stops.NAME);
        onCreate(db);
    }


}
