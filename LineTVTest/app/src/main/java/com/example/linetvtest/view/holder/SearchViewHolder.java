package com.example.linetvtest.view.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.linetvtest.R;
import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;
import com.example.linetvtest.view.activity.DetailActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SearchViewHolder extends BaseViewHolder
{
    private Context mContext;
    private ImageView mImage;
    private TextView mName;
    private TextView mTime;
    private ArrayList<Info> mList;

    public SearchViewHolder(Context context, View view, ArrayList<Info> list)
    {
        super(view);
        mContext = context;
        mImage = view.findViewById(R.id.search_program_image);
        mName = view.findViewById(R.id.search_program_name);
        mTime = view.findViewById(R.id.search_program_time);
        mList = list;

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION)
                {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(Constants.KEY_INFO_ID, mList.get(position).getId());
                    intent.putExtra(Constants.KEY_NAME, mList.get(position).getName());
                    intent.putExtra(Constants.KEY_RATING, mList.get(position).getRating());
                    intent.putExtra(Constants.KEY_TIME, mList.get(position).getTime());
                    intent.putExtra(Constants.KEY_VIEWS_COUNT, mList.get(position).getViewsCount());

                    if(mImage.getDrawable() == null)
                    {
                        Toast.makeText(mContext, Constants.DOWNLOAD_IMAGE_MSG, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Bitmap bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra(Constants.KEY_IMAGE, bytes);
                    }

                    mContext.startActivity(intent);
                }
            }
        });
    }

    public ImageView getImage()
    {
        return mImage;
    }

    public TextView getName()
    {
        return mName;
    }

    public TextView getTime()
    {
        return mTime;
    }
}