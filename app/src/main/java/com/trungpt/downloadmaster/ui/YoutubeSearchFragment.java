package com.trungpt.downloadmaster.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
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
import com.trungpt.downloadmaster.ui.adapter.CountryCodeAdapter;
import com.trungpt.downloadmaster.ui.adapter.SpinnerCommonAdapter;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskSearchVideo;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskListener;
import com.trungpt.downloadmaster.ui.listener.EndlessScrollListener;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.model.YoutubeInfo;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/15/2015.
 */
public class YoutubeSearchFragment extends BaseFragment implements AsyncTaskListener
{
    Animation inAnimation;
    Animation outAnimation;
    private CountryCodeAdapter countryCodeAdapter;
    private SpinnerCommonAdapter orderAdapter;
    private SpinnerCommonAdapter typeAdapter;
    private SpinnerCommonAdapter duarationAdapter;
    private SpinnerCommonAdapter definitionAdapter;
    private SpinnerCommonAdapter videoTypeAdapter;
    YoutubeInfo youtubeInfo;
    CommonAdapter adapter;
    String nextPageToken = "";
    boolean isNextpage = true;

    @Bind(R.id.youtube_search_fragment_lvSearchResult)
    ListView lvSearchResult;
    @Bind(R.id.youtube_search_fragment_progressBar)
    ProgressBar progressBar;
    @Bind(R.id.youtube_search_fragment_fab)
    FloatingActionButton floatingActionButton;
    private Tracker mTracker;
    //Spinner
//    @Bind(R.id.advanced_youtube_search_layout_spCountry)
//    Spinner spCountry;
//    @Bind(R.id.advanced_youtube_search_layout_spOrder)
//    Spinner spOrder;
//    @Bind(R.id.advanced_youtube_search_layout_spType)
//    Spinner spType;
//    @Bind(R.id.advanced_youtube_search_layout_spVideoDuration)
//    Spinner spVideoDuration;
//    @Bind(R.id.advanced_youtube_search_layout_spVideoDefinition)
//    Spinner spVideoDefinition;
//    @Bind(R.id.advanced_youtube_search_layout_spVideoType)
//    Spinner spVideoType;


    @Override
    public int getLayout()
    {
        return R.layout.youtube_search_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        floatingActionButton.setColorNormal(getResources().getColor(R.color.material_purple_500));
        floatingActionButton.setColorPressed(getResources().getColor(R.color.material_light_yellow_800));
        floatingActionButton.setColorRipple(getResources().getColor(R.color.material_yellow_50));
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener()
        {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount)
            {
                if (isNextpage)
                {
                    youtubeInfo.setPageToken(nextPageToken);
                    AsyncTaskSearchVideo taskSearchVideo = new AsyncTaskSearchVideo(getActivity()
                            , Configs.SEARCH_MODE.YOUTUBE, youtubeInfo);
                    taskSearchVideo.setListener(YoutubeSearchFragment.this);
                    taskSearchVideo.execute();
                    return true;
                }
                else
                {
                    return false;
                }
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
        inAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.in_animation);
        outAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.out_animation);
        setupSearch();
//        lvSearchResult.setOnScrollListener(new EndlessScrollListener()
//        {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount)
//            {
//                if (isNextpage)
//                {
//                    youtubeInfo.setPageToken(nextPageToken);
//                    AsyncTaskSearchVideo taskSearchVideo = new AsyncTaskSearchVideo(getActivity()
//                            , Configs.SEARCH_MODE.YOUTUBE, youtubeInfo);
//                    taskSearchVideo.setListener(YoutubeSearchFragment.this);
//                    taskSearchVideo.execute();
//                    return true;
//                }
//                else
//                {
//                    return false;
//                }
//            }
//        });
    }

    public void searchData(YoutubeInfo youtubeInfo)
    {
        adapter = null;
        AsyncTaskSearchVideo taskSearchVideo = new AsyncTaskSearchVideo(getActivity()
                , Configs.SEARCH_MODE.YOUTUBE, youtubeInfo);
        taskSearchVideo.setListener(this);
        taskSearchVideo.execute();
    }

    public void setupSearch()
    {
//        countryCodeAdapter = new CountryCodeAdapter(getActivity(),
//                R.layout.spinner_item);
//        countryCodeAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
//        spCountry.setAdapter(countryCodeAdapter);
//
//        orderAdapter = new SpinnerCommonAdapter(getActivity(), R.layout.spinner_item
//                , getActivity().getResources().getStringArray(R.array.order));
//        orderAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
//        spOrder.setAdapter(orderAdapter);
//
//        typeAdapter = new SpinnerCommonAdapter(getActivity(), R.layout.spinner_item
//                , getActivity().getResources().getStringArray(R.array.type));
//        typeAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
//        spType.setAdapter(typeAdapter);
//
//        duarationAdapter = new SpinnerCommonAdapter(getActivity(), R.layout.spinner_item
//                , getActivity().getResources().getStringArray(R.array.videoDuration));
//        duarationAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
//        spVideoDuration.setAdapter(duarationAdapter);
//
//        definitionAdapter = new SpinnerCommonAdapter(getActivity(), R.layout.spinner_item
//                , getActivity().getResources().getStringArray(R.array.videoDefinition));
//        definitionAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
//        spVideoDefinition.setAdapter(definitionAdapter);
//
//        videoTypeAdapter = new SpinnerCommonAdapter(getActivity(), R.layout.spinner_item
//                , getActivity().getResources().getStringArray(R.array.videoType));
//        videoTypeAdapter.setDropDownViewResource(R.layout.spinner_selector_item);
//        spVideoType.setAdapter(videoTypeAdapter);
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
        isNextpage = pageVideosInfo.isNextpage();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void reload(Object o)
    {
        super.reload(o);
        if (o instanceof YoutubeInfo)
        {
            searchData((YoutubeInfo) o);
            youtubeInfo = (YoutubeInfo) o;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Image~" + YoutubeSearchFragment.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
