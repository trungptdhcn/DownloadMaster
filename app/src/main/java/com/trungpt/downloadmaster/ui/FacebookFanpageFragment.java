package com.trungpt.downloadmaster.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.ui.adapter.FacebookFanpageAdapter;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskFacebook;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskSearchVideo;
import com.trungpt.downloadmaster.ui.listener.AsyncFacebookListener;
import com.trungpt.downloadmaster.ui.listener.EndlessScrollListener;
import com.trungpt.downloadmaster.ui.model.FacebookInfo;
import com.trungpt.downloadmaster.ui.sync.facebook.FacebookFanpage;
import com.trungpt.downloadmaster.ui.sync.facebook.FacebookFanpageList;
import com.trungpt.downloadmaster.utils.Configs;
import org.json.JSONArray;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Trung on 11/20/2015.
 */
public class FacebookFanpageFragment extends BaseFragment implements AsyncFacebookListener
{
    @Bind(R.id.facebook_fanpage_fragment_lvFanpage)
    ListView lvListFanpage;

    @Bind(R.id.youtube_search_fragment_fab)
    FloatingActionButton floatingActionButton;

    @Bind(R.id.facebook_fanpage_fragment_progressBar)
    ProgressBar progressBar;
    private FacebookFanpageAdapter adapter;
    FacebookInfo facebookInfo;
    String nextPage = "";
    private Tracker mTracker;

    @Bind(R.id.list_fragment_native_adsView)
    AdView adView;
    @Override
    public int getLayout()
    {
        return R.layout.facebook_fanpage_fragment;
    }


    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener()
        {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount)
            {
                mostPopular(facebookInfo);
                return true;
            }
        };
        floatingActionButton.attachToListView(lvListFanpage, null, endlessScrollListener);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lvListFanpage.smoothScrollToPosition(0);
            }
        });

    }

    public void mostPopular(FacebookInfo facebookInfo)
    {
        if (AccessToken.getCurrentAccessToken() != null)
        {
            Bundle params = new Bundle();
            params.putString("type", "page");
            params.putString("fields", "id,name,picture,likes,description");
            params.putString("q", facebookInfo.getKeyword());
            params.putString("after", nextPage);
            params.putInt("limit", 5);
            GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken()
                    , "search"
                    , params
                    , HttpMethod.GET);
            AsyncTaskFacebook asyncTaskFacebook = new AsyncTaskFacebook(graphRequest);
            asyncTaskFacebook.setListener(this);
            asyncTaskFacebook.execute();
        }
        else
        {
            Toast.makeText(getActivity(),"Please login to Facebook before use this action!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Image~" + FacebookFanpageFragment.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void reload(Object o)
    {
        super.reload(o);
        adapter = null;
        mostPopular((FacebookInfo) o);
        facebookInfo = (FacebookInfo) o;
    }

    @Override
    public void prepare()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void complete(Object obj)
    {
        String result = ((GraphResponse) obj).getRawResponse();
        if (result != null)
        {
            FacebookFanpageList facebookFanpageList = new Gson().fromJson(result, FacebookFanpageList.class);
            if (facebookFanpageList != null && facebookFanpageList.getFacebookFanpageList().size() > 0)
            {
                if (null == adapter)
                {
                    adapter = new FacebookFanpageAdapter(facebookFanpageList.getFacebookFanpageList(), getActivity());
                    adapter.setFragment(getParentFragment());
                    if (lvListFanpage.getHeaderViewsCount() < 1)
                    {
                        View viewHeader = getActivity().getLayoutInflater().inflate(R.layout.ads_banner_item, lvListFanpage, false);
                        AdView mAdView = (AdView) viewHeader.findViewById(R.id.adView);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                        lvListFanpage.addHeaderView(viewHeader);
                    }
                    lvListFanpage.setAdapter(adapter);
                }
                else
                {
                    adapter.getFacebookFanpage().addAll(facebookFanpageList.getFacebookFanpageList());
                    adapter.notifyDataSetChanged();
                }
                nextPage = facebookFanpageList.getPaging().getCursors().getAfter();
            }
        }
        progressBar.setVisibility(View.GONE);
    }
}
