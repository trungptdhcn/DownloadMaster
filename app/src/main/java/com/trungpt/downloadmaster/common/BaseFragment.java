package com.trungpt.downloadmaster.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by Trung on 10/13/2015.
 */
public abstract class BaseFragment extends Fragment
{
    private View rootView;
    private String TAG = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        setDataToView(savedInstanceState);
        rootView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return onTouchEvent(event);
            }
        });
        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    public void setTAG(String TAG)
    {
        this.TAG = TAG;
    }

    public String getTAG()
    {
        return TAG;
    }

    public boolean isLandscapeFragment()
    {
        return true;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    public abstract int getLayout();

    public abstract void setDataToView(Bundle savedInstanceState);

    public void reload(Object o)
    {

    }

    public void onBackPressed()
    {

    }

}
