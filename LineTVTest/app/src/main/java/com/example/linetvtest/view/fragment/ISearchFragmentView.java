package com.example.linetvtest.view.fragment;

public interface ISearchFragmentView
{
    public void onData(String data);
    public void onError(String error);
    public void onCacheData();
}