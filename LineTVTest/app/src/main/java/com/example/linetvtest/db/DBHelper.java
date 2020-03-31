package com.example.linetvtest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.linetvtest.util.Constants;

import static android.provider.BaseColumns._ID;
import static com.example.linetvtest.db.DBColumn.BUCKETS_COUNT;
import static com.example.linetvtest.db.DBColumn.IMAGE_URL;
import static com.example.linetvtest.db.DBColumn.LIKES_COUNT;
import static com.example.linetvtest.db.DBColumn.NAME;
import static com.example.linetvtest.db.DBColumn.PUBLISH_TIME;
import static com.example.linetvtest.db.DBColumn.RATING;
import static com.example.linetvtest.db.DBColumn.SAVE_TIME;
import static com.example.linetvtest.db.DBColumn.TABLE_NAME;
import static com.example.linetvtest.db.DBColumn.VIEWS_COUNT;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(Context context)
    {
        super(context, Constants.DATABASE_NAME,null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String INIT_TABLE = "create table " + TABLE_NAME + "("+_ID+" integer primary key autoincrement, " + SAVE_TIME + " char," + NAME + " char," + RATING + " char," + PUBLISH_TIME + " char," + IMAGE_URL + " char," + VIEWS_COUNT + " char," + LIKES_COUNT + " char," + BUCKETS_COUNT + " char)";
        db.execSQL(INIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        final String DROP_TABLE = "drop table if exists " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}