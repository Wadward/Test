package com.example.linetvtest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linetvtest.R;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.view.holder.SearchViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter implements Filterable
{
    private static final String TAG = "ProgramListAdapter";
    private Context mContext;
    private ArrayList<Info> mList;
    private ArrayList<Info> mListSearch;
    private Filter mFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<Info> filterList = new ArrayList<>();

            if ((constraint == null) || (constraint.length() == 0))
            {
                filterList.addAll(mListSearch);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Info info : mListSearch)
                {
                    if (info.getName().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(info);
                    }
                }
            }

            FilterResults result = new FilterResults();
            result.values = filterList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults result)
        {
            mList.clear();
            mList.addAll((List) result.values);
            notifyDataSetChanged();
        }
    };

    public SearchListAdapter(@NonNull Context context, @NonNull ArrayList<Info> list, @NonNull ArrayList<Info> listSearch)
    {
        mContext = context;
        mList = list;
        mListSearch = listSearch;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_search, parent, false);
        return new SearchViewHolder(mContext, view, mList);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        final int viewType = getItemViewType(position);

        if(viewType == Constants.VIEW_TYPE_SEARCH)
        {
            Info info = mList.get(position);

            if(info != null)
            {
                SearchViewHolder searchViewHolder = (SearchViewHolder) holder;
                searchViewHolder.getName().setText(info.getName());
                String imageURL = info.getImageURL();

                if((imageURL != null) && (imageURL.length() != 0))
                {
                    Picasso.get()
                            .load(imageURL)
                            .fit()
                            .into(searchViewHolder.getImage());
                }
                else
                {
                    searchViewHolder.getImage().setImageResource(R.drawable.placeholder);
                }

                searchViewHolder.getTime().setText(info.getTime());
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return Constants.VIEW_TYPE_SEARCH;
    }

    @Override
    public Filter getFilter()
    {
        return mFilter;
    }
}