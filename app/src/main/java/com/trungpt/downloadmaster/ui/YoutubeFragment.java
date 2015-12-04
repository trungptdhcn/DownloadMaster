package com.trungpt.downloadmaster.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import butterknife.OnItemClick;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.melnykov.fab.FloatingActionButton;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskListener;
import com.trungpt.downloadmaster.ui.adapter.CommonAdapter;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskGetVideo;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.utils.Configs;
import com.trungpt.downloadmaster.common.StringUtils;

/**
 * Created by Trung on 11/11/2015.
 */
public class YoutubeFragment extends BaseFragment implements AsyncTaskListener
{
    @Bind(R.id.list_fragment_progressBar)
    public ProgressBar progressBar;
    @Bind(R.id.list_fragment_lvYoutube)
    public ListView listView;
    @Bind(R.id.list_fragment_ivNotFund)
    ImageView ivNotFound;
    @Bind(R.id.list_fragment_fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.list_fragment_native_adsView)
    AdView adView;
    CommonAdapter adapter;
    String region;
    private Tracker mTracker;

    @Override
    public int getLayout()
    {
        return R.layout.list_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        firstLoadData(StringUtils.isEmpty(region) ? "us" : region);
        floatingActionButton.setColorNormal(getResources().getColor(R.color.material_purple_500));
        floatingActionButton.setColorPressed(getResources().getColor(R.color.material_light_yellow_800));
        floatingActionButton.setColorRipple(getResources().getColor(R.color.material_yellow_50));
        floatingActionButton.attachToListView(listView);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listView.smoothScrollToPosition(0);
            }
        });
    }

    private void firstLoadData(String region)
    {
        AsyncTaskGetVideo taskGetVideo = new AsyncTaskGetVideo(getActivity(), Configs.HOST_NAME.YOUTUBE, region);
        taskGetVideo.setListener(this);
        taskGetVideo.execute();
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
            if (pageVideosInfo.getListVideos() != null && pageVideosInfo.getListVideos().size() > 0)
            {
                ivNotFound.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if (adapter == null)
                {
                    adapter = new CommonAdapter(pageVideosInfo.getListVideos(), getActivity());
                    adapter.setFragment(this);
                    if (getActivity() != null && getActivity().getLayoutInflater() != null)
                    {
                        View viewHeader = getActivity().getLayoutInflater().inflate(R.layout.ads_banner_item, listView, false);
                        AdView mAdView = (AdView) viewHeader.findViewById(R.id.adView);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                        listView.addHeaderView(viewHeader);
                    }
                    listView.setAdapter(adapter);
                }
                else
                {
                    adapter.getVideoItems().clear();
                    adapter.setVideoItems(pageVideosInfo.getListVideos());
                    adapter.notifyDataSetChanged();
                }
            }
            else
            {
                ivNotFound.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }
        else
        {
            ivNotFound.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void reload(Object o)
    {
        super.reload(o);
        firstLoadData((String) o);
    }

    @OnItemClick(R.id.list_fragment_lvYoutube)
    public void clickItem(int position)
    {
        Log.e("trung dai ca", position + "");
    }


    @Override
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Image~" + YoutubeFragment.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

//    ===============================YOUTUBE PLAYER=====================

//    private void checkYouTubeApi()
//    {
//        YouTubeInitializationResult errorReason =
//                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());
//        if (errorReason.isUserRecoverableError())
//        {
//            errorReason.getErrorDialog(getActivity(), 1).show();
//        }
//        else if (errorReason != YouTubeInitializationResult.SUCCESS)
//        {
//            String errorMessage =
//                    String.format("There was an error initializing the YouTubePlayer (%1$s)", errorReason.toString());
//            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public static final class YoutubeVideoFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener
//    {
//        private YouTubePlayer player;
//        private String videoId;
//
//        public static YoutubeVideoFragment newInstance()
//        {
//            return new YoutubeVideoFragment();
//        }
//
//        @Override
//        public void onCreate(Bundle bundle)
//        {
//            super.onCreate(bundle);
//            initialize(Configs.YOUTUBE_KEY, this);
//        }
//
//        @Override
//        public void onDestroy()
//        {
//            if (player != null)
//            {
//                player.release();
//            }
//            super.onDestroy();
//        }
//
//        public void pause()
//        {
//            if (player != null)
//            {
//                player.pause();
//            }
//        }
//
//        public void setVideoId(String videoId)
//        {
//            if (videoId != null && !videoId.equals(this.videoId))
//            {
//                this.videoId = videoId;
//                if (player != null)
//                {
//                    player.cueVideo(videoId);
//                }
//            }
//        }
//
//
//        @Override
//        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
//        {
//            this.player = youTubePlayer;
//            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
////        player.setOnFullscreenListener((VideoListDemoActivity) getActivity());
////        if (!restored && videoId != null)
////        {
////            player.cueVideo(videoId);
////        }
//        }
//
//        @Override
//        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
//        {
//            this.player = null;
//        }
//    }

}
