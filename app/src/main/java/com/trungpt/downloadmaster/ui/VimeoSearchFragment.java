package com.trungpt.downloadmaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.melnykov.fab.FloatingActionButton;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.ui.adapter.CommonAdapter;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskSearchVideo;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskListener;
import com.trungpt.downloadmaster.ui.listener.EndlessScrollListener;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.model.VimeoInfo;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/15/2015.
 */
public class VimeoSearchFragment extends BaseFragment implements AsyncTaskListener
{
    String nextPageToken = "";

    @Bind(R.id.vimeo_search_fragment_lvSearchResult)
    ListView lvSearchResult;
    @Bind(R.id.vimeo_search_fragment_progressBar)
    ProgressBar progressBar;
    @Bind(R.id.vimeo_search_fragment_fab)
    FloatingActionButton floatingActionButton;
    CommonAdapter adapter;
    VimeoInfo vimeoInfo;
    private Tracker mTracker;

    @Override
    public int getLayout()
    {
        return R.layout.vimeo_search_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        floatingActionButton.setColorNormal(getResources().getColor(R.color.blue_vimeo));
        floatingActionButton.setColorPressed(getResources().getColor(R.color.material_blue_500));
        floatingActionButton.setColorRipple(getResources().getColor(R.color.blue_vimeo));
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener()
        {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount)
            {
                vimeoInfo.setPageToken(page + "");
                AsyncTaskSearchVideo taskSearchVideo = new AsyncTaskSearchVideo(getActivity()
                        , Configs.SEARCH_MODE.VIMEO, vimeoInfo);
                taskSearchVideo.setListener(VimeoSearchFragment.this);
                taskSearchVideo.execute();
                return true;
            }
        };
        floatingActionButton.attachToListView(lvSearchResult,null,endlessScrollListener);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lvSearchResult.smoothScrollToPosition(0);
            }
        });
//        lvSearchResult.setOnScrollListener(new EndlessScrollListener()
//        {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount)
//            {
//                vimeoInfo.setPageToken(page + "");
//                AsyncTaskSearchVideo taskSearchVideo = new AsyncTaskSearchVideo(getActivity()
//                        , Configs.SEARCH_MODE.VIMEO, vimeoInfo);
//                taskSearchVideo.setListener(VimeoSearchFragment.this);
//                taskSearchVideo.execute();
//                return true;
//            }
//        });
    }

    @Override
    public void reload(Object o)
    {
        super.reload(o);
        if (o instanceof VimeoInfo)
        {
            searchData((VimeoInfo) o);
            vimeoInfo = (VimeoInfo) o;
        }
    }

    @Override
    public void prepare()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void complete(PageVideosInfo pageVideosInfo)
    {
        if (pageVideosInfo != null)
        {
            if (null == adapter)
            {
                adapter = new CommonAdapter(pageVideosInfo.getListVideos(), getActivity());
                adapter.setFragment(this);
                if (lvSearchResult.getHeaderViewsCount() < 1)
                {
                    View viewHeader = getActivity().getLayoutInflater().inflate(R.layout.ads_banner_item, lvSearchResult, false);
                    AdView mAdView = (AdView) viewHeader.findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    lvSearchResult.addHeaderView(viewHeader);
                }
                lvSearchResult.setAdapter(adapter);
            }
            else
            {
                adapter.getVideoItems().addAll(pageVideosInfo.getListVideos());
                adapter.notifyDataSetChanged();
            }
        }
        nextPageToken = pageVideosInfo.getNextPageToken();
        progressBar.setVisibility(View.GONE);
    }

    public void searchData(VimeoInfo vimeoInfo)
    {
        adapter = null;
        AsyncTaskSearchVideo taskSearchVideo = new AsyncTaskSearchVideo(getActivity()
                , Configs.SEARCH_MODE.VIMEO, vimeoInfo);
        taskSearchVideo.setListener(this);
        taskSearchVideo.execute();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Image~" + VimeoSearchFragment.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
