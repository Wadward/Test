package com.example.linetvtest.util.handler;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.linetvtest.R;
import com.example.linetvtest.adapter.ProgramListAdapter;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.presenter.IProgramFragmentPresenter;
import com.example.linetvtest.presenter.ISearchFragmentPresenter;
import com.example.linetvtest.util.parser.JSONParser;
import com.example.linetvtest.view.fragment.IProgramFragmentView;

import java.util.ArrayList;

public class LoadDataHandler extends AsyncTask<Void, Void, Void>
{
    private Context mContext = null;
    private IProgramFragmentPresenter mIProgramFragmentPresenter = null;
    private ISearchFragmentPresenter mISearchFragmentPresenter = null;
    private String mURL = null;
    private ArrayList<Info> mList = null;
    private ArrayList<Info> mListSearch = null;
    private RequestQueue mQueue = null;
    private boolean mLoadMore = false;

    public LoadDataHandler(Context context, IProgramFragmentPresenter programFragmentPresenter, String url, ArrayList<Info> list, boolean loadMore)
    {
        mContext = context;
        mIProgramFragmentPresenter = programFragmentPresenter;
        mURL = url;
        mList = list;
        mQueue = Volley.newRequestQueue(context);
        mLoadMore = loadMore;
    }

    public LoadDataHandler(Context context, ISearchFragmentPresenter searchFragmentPresenter, String url, ArrayList<Info> list, ArrayList<Info> listSearch, boolean loadMore)
    {
        mContext = context;
        mISearchFragmentPresenter = searchFragmentPresenter;
        mURL = url;
        mList = list;
        mListSearch = listSearch;
        mQueue = Volley.newRequestQueue(context);
        mLoadMore = loadMore;
    }

    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        requestProgramData(mURL);
        return null;
    }

    @Override
    protected void onPostExecute(Void v)
    {
    }

    private void requestProgramData(final String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        if((response != null) || (!response.isEmpty()))
                        {
                            if(!mLoadMore)
                            {
                                mList.clear();

                                if(mListSearch != null)
                                {
                                    mListSearch.clear();
                                }
                            }

                            JSONParser.parseProgramData(response, mList, mListSearch);

                            if(mIProgramFragmentPresenter != null)
                            {
                                mIProgramFragmentPresenter.onData(response.toString());
                            }

                            if(mISearchFragmentPresenter != null)
                            {
                                mISearchFragmentPresenter.onData(response.toString());
                            }
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if(mIProgramFragmentPresenter != null)
                        {
                            mIProgramFragmentPresenter.onError(error.toString());
                        }

                        if(mISearchFragmentPresenter != null)
                        {
                            mISearchFragmentPresenter.onError(error.toString());
                        }

                        if(error instanceof NoConnectionError)
                        {
                            Toast.makeText(mContext, mContext.getString(R.string.error_network_no_connection), Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(mContext, mContext.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            //TODO
                        }
                        else if (error instanceof ServerError)
                        {
                            //TODO
                        }
                        else if (error instanceof NetworkError)
                        {
                            //TODO
                        }
                        else if (error instanceof ParseError)
                        {
                            //TODO
                        }
                    }
                });

        mQueue.add(stringRequest);
    }
}