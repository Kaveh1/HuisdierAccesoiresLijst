package com.example.kaveh.huisdieraccesoireslijst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kaveh on 10-3-2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    //Database info
    private static final String DATABASE_NAME = "accessorylist.db";
    private static final int DATABASE_VERSION = 1;

    //Accessories
    public static final String TABLE_ACCESSORIES = "accessory";
    public static final String COLUMN_ACCESSORY_ID = "accessory_id";
    public static final String COLUMN_ACCESSORY_INFO = "accessory_info";
    public static final String COLUMN_ACCESSORY_WEBSITE = "accessory_website";

    //Creating the table
    private static final String DATABASE_CREATE_ACCESSORIES =
            "CREATE TABLE " + TABLE_ACCESSORIES +
                    "(" +
                    COLUMN_ACCESSORY_ID + " integer primary key autoincrement, " +
                    COLUMN_ACCESSORY_INFO + " text not null, " +
                    COLUMN_ACCESSORY_WEBSITE + " text not null" +
                    ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Execute the sql to create the table accessories
        db.execSQL(DATABASE_CREATE_ACCESSORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
     	* When the database gets upgraded you should handle the update to make sure there is no
     	* data loss.This is the default code you put in the upgrade method, to delete the table
     	* and call the oncreate again.
     	*/
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESSORIES);
        onCreate(db);
    }
}
