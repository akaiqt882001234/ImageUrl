package com.example.urlimage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME ="URLTable.db";
    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "my_url";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_URL = "urltext";
    private final Context context;

    public DbHelper(Context context) {
        super(context,DB_NAME,null ,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql ="CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_URL + " TEXT) ;";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addURL(String imageUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_URL, imageUrl);
        long result = db.insert(TABLE_NAME,null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Added Successfully !", Toast.LENGTH_SHORT).show();
        }

    }
}
