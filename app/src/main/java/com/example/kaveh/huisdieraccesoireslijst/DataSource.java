package com.example.kaveh.huisdieraccesoireslijst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaveh on 10-3-2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] accessoryAllColumns = {MySQLiteHelper.COLUMN_ACCESSORY_ID,
            MySQLiteHelper.COLUMN_ACCESSORY_INFO, MySQLiteHelper.COLUMN_ACCESSORY_WEBSITE};

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws android.database.SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    public long createAccessory(String accessory, String website) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ACCESSORY_INFO, accessory);
        values.put(MySQLiteHelper.COLUMN_ACCESSORY_WEBSITE, website);
        long insertId = database.insert(MySQLiteHelper.TABLE_ACCESSORIES, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();
        return insertId;
    }

    public void deleteAssignment(Accessory accessory) {
        if (!database.isOpen())
            open();

        database.delete(MySQLiteHelper.TABLE_ACCESSORIES, MySQLiteHelper.COLUMN_ACCESSORY_ID +
                " =?", new String[] {Long.toString(accessory.getId())});

        if (database.isOpen())
            close();

    }

    public void updateAccessory(Accessory accessory) {
        if (!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_ACCESSORY_INFO, accessory.getAccessory());
        args.put(MySQLiteHelper.COLUMN_ACCESSORY_WEBSITE, accessory.getWebsite());
        database.update(MySQLiteHelper.TABLE_ACCESSORIES, args, MySQLiteHelper.COLUMN_ACCESSORY_ID +
                "=?", new String[]{Long.toString(accessory.getId())});

        if (database.isOpen())
            close();
    }

    public List<Accessory> getAllAccessories() {
        if (!database.isOpen())
            open();

        List<Accessory> accessories = new ArrayList<Accessory>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ACCESSORIES, accessoryAllColumns, null,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Accessory accessory = cursorToAccessory(cursor);
            accessories.add(accessory);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return accessories;
    }

    private Accessory cursorToAccessory(Cursor cursor) {
        try {
            Accessory accessory = new Accessory();
            accessory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ACCESSORY_ID)));
            accessory.setAccessory(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ACCESSORY_INFO)));
            accessory.setWebsite(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_ACCESSORY_WEBSITE)));
            return accessory;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Accessory getAccessory(long columnId) {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ACCESSORIES, accessoryAllColumns,
                MySQLiteHelper.COLUMN_ACCESSORY_ID + "=?", new String[]{Long.toString(columnId)},
                null, null, null);

        cursor.moveToFirst();
        Accessory accessory = cursorToAccessory(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return accessory;
    }
}
