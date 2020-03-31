package com.example.linetvtest.view.fragment;

public interface IProgramFragmentView
{
    public void onData(String data);
    public void onError(String error);
    public void onCacheData();
}