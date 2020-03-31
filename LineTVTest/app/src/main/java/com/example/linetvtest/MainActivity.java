package com.example.linetvtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.linetvtest.db.DBManager;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.view.activity.SearchActivity;
import com.example.linetvtest.view.fragment.ProgramFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "MainActivity";
    private ProgramFragment mProgramFragment = null;
    private DBManager mDBManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        mDBManager = new DBManager(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);
        mProgramFragment = (ProgramFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        ArrayList<Info> list = mProgramFragment.getProgramList();

        for(Info info : list)
        {
            mDBManager.add(info);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_version)
        {
            Toast.makeText(this, Constants.VERSION, Toast.LENGTH_LONG).show();
        }

        if(id == R.id.action_search)
        {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}