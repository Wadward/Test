package com.example.linetvtest.view.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.linetvtest.R;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.view.activity.DetailActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class InfoViewHolder extends BaseViewHolder
{
    private Context mContext;
    private ImageView mImage;
    private TextView mName;
    private TextView mRating;
    private TextView mTime;
    private TextView mLikesCount;
    private TextView mBucketsCount;
    private ArrayList<Info> mList;

    public InfoViewHolder(Context context, View view, ArrayList<Info> list)
    {
        super(view);
        mContext = context;
        mImage = view.findViewById(R.id.program_image);
        mName = view.findViewById(R.id.program_name);
        mRating = view.findViewById(R.id.program_rating);
        mTime = view.findViewById(R.id.program_time);
        mLikesCount = view.findViewById(R.id.program_like_count);
        mBucketsCount = view.findViewById(R.id.program_bucket_count);
        mList = list;
    }

    public ImageView getImage()
    {
        return mImage;
    }

    public TextView getName()
    {
        return mName;
    }

    public TextView getRating()
    {
        return mRating;
    }

    public TextView getTime()
    {
        return mTime;
    }

    public TextView getLikesCount()
    {
        return mLikesCount;
    }

    public TextView getBucketsCount()
    {
        return mBucketsCount;
    }
}