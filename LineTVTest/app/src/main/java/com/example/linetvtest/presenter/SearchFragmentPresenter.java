package com.example.linetvtest.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.linetvtest.db.DBManager;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.handler.LoadDataHandler;
import com.example.linetvtest.view.fragment.ISearchFragmentView;

import java.util.ArrayList;

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

public class SearchFragmentPresenter implements ISearchFragmentPresenter
{
    private Context mContext = null;
    private ISearchFragmentView mSearchFragmentView = null;
    private DBManager mDBManager = null;
    private ArrayList<Info> mList = null;
    private ArrayList<Info> mListSearch = null;

    public SearchFragmentPresenter(Context context, ISearchFragmentView searchFragmentView, ArrayList<Info> list, ArrayList<Info> listSearch)
    {
        mContext = context;
        mSearchFragmentView = searchFragmentView;
        mList = list;
        mListSearch = listSearch;
    }

    @Override
    public void onData(String data)
    {
        mSearchFragmentView.onData(data);
    }

    @Override
    public void onError(String error)
    {
        mSearchFragmentView.onError(error);
    }

    public void refresh(String url, boolean loadMore)
    {
        new LoadDataHandler(mContext, this, url, mList, mListSearch, loadMore).execute();
    }

    public void showCacheData()
    {
        Cursor cursor = getCursor();

        while (cursor.moveToNext())
        {
            Info info = new Info();
            info.setId(cursor.getString(0));
            info.setName(cursor.getString(2));
            info.setViewsCount(cursor.getString(6));
            info.setTime(cursor.getString(4));
            info.setImageURL(cursor.getString(5));
            info.setRating(cursor.getString(3));
            info.setLikesCount(Integer.valueOf(cursor.getString(7)));
            info.setBucketsCount(Integer.valueOf(cursor.getString(8)));
            mList.add(info);

            if(mListSearch != null)
            {
                mListSearch.add(info);
            }
        }

        mDBManager.closeDB();
        mSearchFragmentView.onCacheData();
    }

    private Cursor getCursor()
    {
        mDBManager = new DBManager(mContext);
        SQLiteDatabase db = mDBManager.getDB();
        String[] columns = {_ID, SAVE_TIME, NAME, RATING, PUBLISH_TIME, IMAGE_URL, VIEWS_COUNT, LIKES_COUNT, BUCKETS_COUNT};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }
}