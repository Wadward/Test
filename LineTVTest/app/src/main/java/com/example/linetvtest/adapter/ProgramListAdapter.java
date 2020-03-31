package com.example.linetvtest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linetvtest.R;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.view.holder.InfoViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProgramListAdapter extends RecyclerView.Adapter
{
    private static final String TAG = "ProgramListAdapter";
    private boolean mShowLoading;
    private Context mContext;
    private LoadMoreListener mLoadMoreListener;
    private ArrayList<Info> mList;

    public ProgramListAdapter(@NonNull Context context, @NonNull ArrayList<Info> list, @NonNull LoadMoreListener loadMoreListener)
    {
        mList = list;
        mLoadMoreListener = loadMoreListener;
        mShowLoading = true;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == Constants.VIEW_TYPE_PROGRAM)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_program, parent, false);
            return new InfoViewHolder(mContext, view, mList);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_loading, parent, false);
            return new RecyclerView.ViewHolder(view)
            {
            };
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        final int viewType = getItemViewType(position);

        if(viewType == Constants.VIEW_TYPE_LOADING)
        {
            mLoadMoreListener.onLoadMore();
        }
        else
        {
            Info info = mList.get(position);

            if(info != null)
            {
                InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
                infoViewHolder.getName().setText(info.getName());
                String imageURL = info.getImageURL();

                if((imageURL != null) && (imageURL.length() != 0))
                {
                    Picasso.get()
                        .load(imageURL)
                        .fit()
                        .into(infoViewHolder.getImage());
                }
                else
                {
                    infoViewHolder.getImage().setImageResource(R.drawable.placeholder);
                }

                infoViewHolder.getRating().setText(info.getRating());
                infoViewHolder.getTime().setText(info.getTime());
                infoViewHolder.getLikesCount().setText(String.valueOf(info.getLikesCount()));
                infoViewHolder.getBucketsCount().setText(String.valueOf(info.getBucketsCount()));
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return mShowLoading ? mList.size() + 1 : mList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position < mList.size() ? Constants.VIEW_TYPE_PROGRAM : Constants.VIEW_TYPE_LOADING;
    }

    public void append(@NonNull List<Info> list)
    {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getDataCount()
    {
        return mList.size();
    }

    public void setShowLoading(boolean showLoading)
    {
        mShowLoading = showLoading;
        notifyDataSetChanged();
    }

    public interface LoadMoreListener
    {
        void onLoadMore();
    }
}