package com.example.linetvtest.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linetvtest.R;
import com.example.linetvtest.adapter.SearchListAdapter;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.presenter.ProgramFragmentPresenter;
import com.example.linetvtest.presenter.SearchFragmentPresenter;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.util.handler.LoadDataHandler;
import com.example.linetvtest.view.activity.SearchActivity;
import com.example.linetvtest.view.base.SpaceItemDecoration;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements ISearchFragmentView
{
    private final static String TAG = "SearchFragment";
    private SearchFragmentPresenter mSearchFragmentPresenter = null;
    private RecyclerView mRecyclerView = null;
    private SearchListAdapter mAdapter = null;
    private ArrayList<Info> mList = new ArrayList<>();
    private ArrayList<Info> mListSearch = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mSearchFragmentPresenter = new SearchFragmentPresenter(getContext(), this, mList, mListSearch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        mRecyclerView = getView().findViewById(R.id.search_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));
        mSearchFragmentPresenter.refresh(Constants.URL, false);
        mAdapter = new SearchListAdapter(this.getContext(), mList, mListSearch);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onData(String data)
    {
        mAdapter.notifyDataSetChanged();
        SearchActivity activity = (SearchActivity) getActivity();
        activity.changeQueryText();
    }

    @Override
    public void onError(String error)
    {
        if(mList.size() == 0)
        {
            mSearchFragmentPresenter.showCacheData();
        }
    }

    @Override
    public void onCacheData()
    {
        mAdapter.notifyDataSetChanged();
        SearchActivity activity = (SearchActivity) getActivity();
        activity.changeQueryText();
    }

    public SearchListAdapter getAdapter()
    {
        return mAdapter;
    }
}