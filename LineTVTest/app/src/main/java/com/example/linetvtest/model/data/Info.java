package com.example.linetvtest.model.data;

public class Info
{
    private int mLikesCount;
    private int mBucketsCount;
    private String mID;
    private String mName;
    private String mViewsCount;
    private String mTime;
    private String mURLToImage;
    private String mRating;

    public String getId()
    {
        return mID;
    }

    public void setId(String id)
    {
        mID = id;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getViewsCount()
    {
        return mViewsCount;
    }

    public void setViewsCount(String count)
    {
        mViewsCount = count;
    }

    public String getTime()
    {
        return mTime;
    }

    public void setTime(String time)
    {
        mTime = time;
    }

    public String getImageURL()
    {
        return mURLToImage;
    }

    public void setImageURL(String url)
    {
        mURLToImage = url;
    }

    public String getRating()
    {
        return mRating;
    }

    public void setRating(String rating)
    {
        mRating = rating;
    }

    public int getLikesCount()
    {
        return mLikesCount;
    }

    public void setLikesCount(int count)
    {
        mLikesCount = count;
    }

    public int getBucketsCount()
    {
        return mBucketsCount;
    }

    public void setBucketsCount(int count)
    {
        mBucketsCount = count;
    }
}