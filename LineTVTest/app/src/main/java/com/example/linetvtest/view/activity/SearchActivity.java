package com.example.linetvtest.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.example.linetvtest.R;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.view.fragment.ProgramFragment;
import com.example.linetvtest.view.fragment.SearchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{
    private final static String TAG = "SearchActivity";
    private Toolbar mToolbar = null;
    private SearchView mSearchView = null;
    private String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        mQuery = prefs.getString(Constants.QUERY, "");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.QUERY, mQuery);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                mQuery = query;
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text)
            {
                mQuery = text;
                changeQueryText();
                return false;
            }
        });

        mSearchView.setQuery(mQuery, false);
        return true;
    }

    public void changeQueryText()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment2);
        SearchFragment searchFragment = (SearchFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        searchFragment.getAdapter().getFilter().filter(mQuery);
    }
}