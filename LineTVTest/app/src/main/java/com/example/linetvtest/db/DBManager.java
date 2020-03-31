package com.example.linetvtest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.example.linetvtest.model.data.Info;

import java.util.Date;
import java.util.Locale;

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

public class DBManager
{
    private final static String TAG = "DBManager";
    private Context mContext = null;
    private DBHelper mDBHelper = null;

    public DBManager(Context context)
    {
        mContext = context;
        mDBHelper = new DBHelper(mContext);
    }

    public SQLiteDatabase getDB()
    {
        return mDBHelper.getReadableDatabase();
    }

    public void closeDB()
    {
        mDBHelper.close();
    }

    public void add(Info info)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, info.getId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        values.put(SAVE_TIME, simpleDateFormat.format(new Date()));
        values.put(NAME, info.getName());
        values.put(RATING, info.getRating());
        values.put(PUBLISH_TIME, info.getTime());
        values.put(IMAGE_URL, info.getImageURL());
        values.put(VIEWS_COUNT, info.getViewsCount());
        values.put(LIKES_COUNT, String.valueOf(info.getLikesCount()));
        values.put(BUCKETS_COUNT, String.valueOf(info.getBucketsCount()));

        try
        {
            db.insert(TABLE_NAME,null, values);
        }
        catch (Exception exception)
        {
            Log.d(TAG, exception.toString());
        }
    }

    public void delete(String id)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete(TABLE_NAME,_ID + "=" + id,null);
    }
}