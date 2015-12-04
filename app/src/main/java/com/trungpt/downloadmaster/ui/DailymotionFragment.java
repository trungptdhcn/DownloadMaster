package com.trungpt.downloadmaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.melnykov.fab.FloatingActionButton;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.common.StringUtils;
import com.trungpt.downloadmaster.ui.adapter.CommonAdapter;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskGetVideo;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskListener;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.utils.Configs;
import org.json.JSONArray;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionFragment extends BaseFragment implements AsyncTaskListener
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
    private Tracker mTracker;
    InterstitialAd mInterstitialAd;

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
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.download_master_integrate));
        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
                requestNewInterstitial();
                listView.smoothScrollToPosition(0);
            }
        });
        requestNewInterstitial();
        floatingActionButton.setColorNormal(getResources().getColor(R.color.blue_vimeo));
        floatingActionButton.setColorPressed(getResources().getColor(R.color.material_blue_500));
        floatingActionButton.setColorRipple(getResources().getColor(R.color.blue_vimeo));
        firstLoadData("music");
        floatingActionButton.attachToListView(listView);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mInterstitialAd.show();
                listView.smoothScrollToPosition(0);
            }
        });
    }
    private void requestNewInterstitial()
    {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("85875FC3563C88A48AD872E6EF2EB7")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void firstLoadData(String id)
    {
        AsyncTaskGetVideo taskGetVideo = new AsyncTaskGetVideo(getActivity(), Configs.HOST_NAME.DAILYMOON, id);
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
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Image~" + DailymotionFragment.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void reload(Object o)
    {
        super.reload(o);
        firstLoadData((String) o);
    }
}
