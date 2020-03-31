package com.example.linetvtest.util.parser;

import android.util.Log;

import com.example.linetvtest.model.data.Info;
import com.example.linetvtest.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class JSONParser
{
    private final static String TAG = "JSONParser";

    public static void parseProgramData(String response, ArrayList<Info> list, ArrayList<Info> listAll)
    {
        if(( response == null ) || ( response.length() == 0 ))
            return;

        Log.d(TAG, "Response: " + response.toString());

        try
        {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray(Constants.KEY_DATA);
            Log.d(TAG, "JSON array length: " + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonOBJ = jsonArray.getJSONObject(i);
                Info info = new Info();
                info.setId(jsonOBJ.getString(Constants.KEY_INFO_ID));
                info.setName(new String(jsonOBJ.getString(Constants.KEY_NAME).getBytes(Constants.ISO_8859_1), Constants.UTF_8));
                info.setViewsCount(jsonOBJ.getString(Constants.KEY_VIEWS_COUNT));
                info.setTime(jsonOBJ.getString(Constants.KEY_TIME));
                info.setImageURL(jsonOBJ.getString(Constants.KEY_IMAGE_URL));
                info.setRating(jsonOBJ.getString(Constants.KEY_RATING));
                Random random = new Random();
                info.setLikesCount(random.nextInt(Constants.KEY_INFO_LIKE_COUNT_RANGE));
                info.setBucketsCount(random.nextInt(Constants.KEY_INFO_BUCKET_COUNT_RANGE));
                list.add(info);

                if(listAll != null)
                {
                    listAll.add(info);
                }
            }
        }
        catch (JSONException | UnsupportedEncodingException exception)
        {
            exception.printStackTrace();
            return;
        }
    }
}