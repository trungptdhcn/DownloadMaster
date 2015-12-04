package com.trungpt.downloadmaster.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.ButterKnife;
import com.facebook.login.LoginManager;
import com.trungpt.downloadmaster.R;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Trung on 10/13/2015.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        setDataToView(savedInstanceState);
    }

    public abstract void setDataToView(Bundle savedInstanceState);

    public abstract int getLayout();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
//        getActionBarToolbar();

    }


    protected Toolbar getActionBarToolbar()
    {
        if (toolbar == null)
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (toolbar != null)
            {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        return toolbar;
    }



}

