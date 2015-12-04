package com.trungpt.downloadmaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.trungpt.downloadmaster.MainActivity;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.ui.adapter.CommonAdapter;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskFacebook;
import com.trungpt.downloadmaster.ui.listener.AsyncFacebookListener;
import com.trungpt.downloadmaster.ui.listener.EndlessScrollListener;
import com.trungpt.downloadmaster.ui.listener.OnBackPressedListener;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.ui.sync.facebook.FacebookVideo;
import com.trungpt.downloadmaster.ui.sync.facebook.FacebookVideoList;
import com.trungpt.downloadmaster.utils.Configs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookVideoFragment extends BaseFragment implements AsyncFacebookListener
{
    @Bind(R.id.facebook_video_fragment_lvYoutube)
    ListView lvVideos;
    @Bind(R.id.facebook_video_fragment_progressBar)
    ProgressBar progressBar;
    CommonAdapter adapter;
    String nextPage = "";
    @Bind(R.id.facebook_video_fragment_fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.facebook_video_fragment_native_adsView)
    AdView adView;
    private Tracker mTracker;

    @Override
    public int getLayout()
    {
        return R.layout.facebook_video_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        final String pageId = getArguments().getString("page_id");
        getVideo(pageId);
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener()
        {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount)
            {
                getVideo(pageId);
                return true;
            }
        };
        floatingActionButton.attachToListView(lvVideos, null, endlessScrollListener);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lvVideos.smoothScrollToPosition(0);
            }
        });
    }

    private void getVideo(String pageId)
    {
        Bundle params = new Bundle();
        params.putString("fields", "source,description,picture");
        params.putString("after", nextPage);
        params.putInt("limit", 5);
        GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken()
                , pageId + "/videos"
                , params
                , HttpMethod.GET);
        AsyncTaskFacebook asyncTaskFacebook = new AsyncTaskFacebook(graphRequest);
        asyncTaskFacebook.setListener(this);
        asyncTaskFacebook.execute();
    }

    @Override
    public void prepare()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void complete(Object obj)
    {
        GraphResponse response = (GraphResponse) obj;
        FacebookVideoList facebookVideoList = new Gson()
                .fromJson(response.getRawResponse(), FacebookVideoList.class);
        List<VideoItem> videoItemList = new ArrayList<>();
        if (facebookVideoList != null && facebookVideoList.getFacebookVideos().size() > 0)
        {
            for (FacebookVideo facebookVideo : facebookVideoList.getFacebookVideos())
            {
                VideoItem videoItem = new VideoItem();
                videoItem.setHost_name(Configs.HOST_NAME.FACEBOOK);
                videoItem.setDescription(facebookVideo.getDescription());
                videoItem.setUrlThumbnail(facebookVideo.getPicture());
                videoItem.setTitle(facebookVideo.getDescription());
                videoItem.setId(facebookVideo.getId());
                videoItem.setUrl(facebookVideo.getSource());
                videoItemList.add(videoItem);
            }
            if (null == adapter)
            {
                adapter = new CommonAdapter(videoItemList, getActivity());
                if (lvVideos.getHeaderViewsCount() < 1)
                {
                    View viewHeader = getActivity().getLayoutInflater().inflate(R.layout.ads_banner_item, lvVideos, false);
                    AdView mAdView = (AdView) viewHeader.findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    lvVideos.addHeaderView(viewHeader);
                }
                lvVideos.setAdapter(adapter);
            }
            else
            {
                adapter.getVideoItems().addAll(videoItemList);
                adapter.notifyDataSetChanged();
            }
            nextPage = facebookVideoList.getPaging().getCursors().getAfter();
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName("Image~" + FacebookVideoFragment.class);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
