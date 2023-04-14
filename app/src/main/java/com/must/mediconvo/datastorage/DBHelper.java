package com.must.mediconvo.datastorage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static final String KEY_ID = "_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER = "username";

    DBHelper DB = null;
    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_TABLE_NAME = "Client";

    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PHONE +
                    " TEXT NOT NULL, " + KEY_USER + " TEXT NOT NULL, " + KEY_EMAIL + " TEXT NOT NULL, password TEXT NOT NULL);";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //    System.out.println("In constructor");
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(DATABASE_TABLE_CREATE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);

        //Create tables again
        onCreate(db);

    }

    public Cursor rawQuery(String string, String[] strings) {
        // TODO Auto-generated method stub
        return null;

    }

    public void open() {
        getWritableDatabase();

    }

    public Cursor getDetails(String text) throws SQLException {

        Cursor mCursor =
                db.query(true, DATABASE_TABLE_NAME, new String[]{KEY_ID, KEY_PHONE, KEY_EMAIL, KEY_USER,},
                        KEY_USER + " = " + text,
                        null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
