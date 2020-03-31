package com.example.linetvtest.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linetvtest.R;
import com.example.linetvtest.util.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar = findViewById(R.id.toolbar_detail);
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

        mImageView = findViewById(R.id.picture);
        mTextView = findViewById(R.id.content);
        setData();
    }

    private void setData()
    {
        Intent intent = getIntent();
        StringBuilder sb = new StringBuilder();
        this.setTitle(intent.getStringExtra(Constants.KEY_INFO_ID));
        sb.append(Constants.PREFIX_DRAMA_NAME).append(Constants.COLON).append(Constants.SPACE).append(intent.getStringExtra(Constants.KEY_NAME)).append(Constants.EOL);
        sb.append(Constants.PREFIX_RATING).append(Constants.COLON).append(Constants.SPACE).append(intent.getStringExtra(Constants.KEY_RATING)).append(Constants.EOL);
        sb.append(Constants.PREFIX_PUBLISH_DATE).append(Constants.COLON).append(Constants.SPACE).append(intent.getStringExtra(Constants.KEY_TIME)).append(Constants.EOL);
        sb.append(Constants.PREFIX_TOTAL_VIEW).append(Constants.COLON).append(Constants.SPACE).append(intent.getStringExtra(Constants.KEY_VIEWS_COUNT));
        mTextView.setText(sb.toString());
        byte[] bytes = getIntent().getByteArrayExtra(Constants.KEY_IMAGE);
        Bitmap bitmap = null;

        if (bytes != null)
        {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.placeholder);
        }

        mImageView.setImageBitmap(bitmap);
    }
}