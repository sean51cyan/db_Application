package com.example.dbapplication;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;

    private static final String DATABASE_NAME = "kadaiDB.db";
    private static final String TABLE_NAME = "授業";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_DATE= "date";
    private static final String TABLE_NAME2 = "課題";
    private static final String COLUMN_NAME_TASK= "task";
    private static final String COLUMN_NAME_DEADLINE= "dl";
    private static final String COLUMN_NAME_FIN= "fin";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + TABLE_NAME2 + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TASK + " TEXT," +
                    COLUMN_NAME_DEADLINE + " DATE," +
                    COLUMN_NAME_FIN + " BOOL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES2 =
            "DROP TABLE IF EXISTS "+ TABLE_NAME2;


    public TestOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                SQL_CREATE_ENTRIES
        );
        db.execSQL(
                SQL_CREATE_ENTRIES2
        );

        Log.d("debug", "onCreate(SQLiteDatabase db)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        db.execSQL(
                SQL_DELETE_ENTRIES2
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}