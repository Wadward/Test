package com.example.linetvtest.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.linetvtest.R;
import com.example.linetvtest.adapter.ProgramListAdapter;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.presenter.ProgramFragmentPresenter;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.util.listener.RecyclerViewTouchListener;
import com.example.linetvtest.view.activity.DetailActivity;
import com.example.linetvtest.view.base.SpaceItemDecoration;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ProgramFragment extends Fragment implements IProgramFragmentView
{
    private final static String TAG = "ProgramFragment";
    private ProgramFragmentPresenter mProgramFragmentPresenter = null;
    private RecyclerView mRecyclerView = null;
    private ProgramListAdapter mAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private ArrayList<Info> mList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mProgramFragmentPresenter = new ProgramFragmentPresenter(getContext(), this, mList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        mRecyclerView = getView().findViewById(R.id.program_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), mRecyclerView, new RecyclerViewTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                if((mList.size() != 0) && (position != mList.size()))
                {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(Constants.KEY_INFO_ID, mList.get(position).getId());
                    intent.putExtra(Constants.KEY_NAME, mList.get(position).getName());
                    intent.putExtra(Constants.KEY_RATING, mList.get(position).getRating());
                    intent.putExtra(Constants.KEY_TIME, mList.get(position).getTime());
                    intent.putExtra(Constants.KEY_VIEWS_COUNT, mList.get(position).getViewsCount());
                    ImageView image = view.findViewById(R.id.program_image);

                    if(image.getDrawable() == null)
                    {
                        Toast.makeText(getActivity(), Constants.DOWNLOAD_IMAGE_MSG, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra(Constants.KEY_IMAGE, bytes);
                    }

                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position)
            {
            }
        }));
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        mSwipeRefreshLayout.setBackgroundResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mProgramFragmentPresenter.refresh(Constants.URL, false);
            }
        });

        mProgramFragmentPresenter.refresh(Constants.URL, false);
        mAdapter = new ProgramListAdapter(this.getContext(), mList, new ProgramListAdapter.LoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                //mProgramFragmentPresenter.refresh(Constants.URL, true);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onData(String data)
    {
        stopRefreshing();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error)
    {
        if(mList.size() == 0)
        {
            mProgramFragmentPresenter.showCacheData();
        }
        else
        {
            stopRefreshing();
        }

        Log.d(TAG, error);
    }

    @Override
    public void onCacheData()
    {
        stopRefreshing();
        mAdapter.notifyDataSetChanged();
    }

    public ArrayList<Info> getProgramList()
    {
        return mList;
    }

    private void stopRefreshing()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }
}