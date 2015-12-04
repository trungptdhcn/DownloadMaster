package com.trungpt.downloadmaster.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.trungpt.downloadmaster.MyApplication;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseActivity;
import com.trungpt.downloadmaster.common.event.DownloadEvent;
import com.trungpt.downloadmaster.download.VideoModel;
import com.trungpt.downloadmaster.ui.asyntask.AsyncTaskGetDirectLink;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskGetLinkListener;
import com.trungpt.downloadmaster.ui.model.VideoItem;
import com.trungpt.downloadmaster.utils.Configs;
import de.greenrobot.event.EventBus;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends BaseActivity implements AsyncTaskGetLinkListener
{
    @Bind(R.id.video_detail_fragment_ivCover)
    ImageView imageCover;
    @Bind(R.id.video_detail_fragment_tvTitle)
    TextView tvTitle;
    @Bind(R.id.video_detail_fragment_tvDescription)
    TextView tvDescription;
    @Bind(R.id.video_detail_fragment_btDownload)
    ImageButton btDownload;
    @Bind(R.id.video_detail_fragment_progressBar)
    ProgressBar progressBar;
    @Bind(R.id.video_detail_fragment_rlContainer)
    RelativeLayout rlContainer;
    List<VideoModel> videoModels = new ArrayList<>();
    AsyncTaskGetDirectLink asyncTaskGetDirectLink;
    private Tracker mTracker;
    InterstitialAd mInterstitialAd;

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.download_master_integrate));
        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
                requestNewInterstitial();
                startDownLoad();
            }
        });
        requestNewInterstitial();
        Intent i = getIntent();
        VideoItem videoItem = (VideoItem) i.getSerializableExtra("videoItem");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (videoItem != null)
        {
            tvTitle.setText(videoItem.getTitle());
            tvDescription.setText(videoItem.getDescription());
            tvDescription.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(this)
                    .load(videoItem.getUrlThumbnail())
                    .asBitmap()
                    .animate(R.anim.abc_fade_in)
                    .placeholder(R.drawable.no_thumbnail)
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(width, height / 3)
                    {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim)
                        {
                            imageCover.setImageBitmap(bitmap);
                        }
                    });
            if (videoItem.getHost_name() != Configs.HOST_NAME.FACEBOOK)
            {
                asyncTaskGetDirectLink = new AsyncTaskGetDirectLink(videoItem.getUrl(), videoItem.getHost_name());
                asyncTaskGetDirectLink.setListener(this);
                asyncTaskGetDirectLink.execute();
            }
            else
            {
                try
                {
                    VideoModel videoModel = new VideoModel();
                    videoModel.setQuantity("MP4");
                    videoModel.setType("MP4");
                    videoModel.setTitle(videoItem.getTitle());
                    videoModel.setUrl(new URL(videoItem.getUrl()));
                    videoModel.setHost_name(Configs.HOST_NAME.FACEBOOK);
                    videoModel.setUrlIcon(videoItem.getUrlThumbnail());
                    videoModels.add(videoModel);
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
            }
        }


    }

    private void requestNewInterstitial()
    {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("85875FC3563C88A48AD872E6EF2EB7")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public int getLayout()
    {
        return R.layout.video_detail_fragment;
    }

    @OnClick(R.id.video_detail_fragment_btDownload)
    public void download()
    {
        if (mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
        else
        {
            startDownLoad();
        }
    }

    private void startDownLoad()
    {
        if (videoModels != null && videoModels.size() > 0)
        {
            CharSequence[] items = new CharSequence[videoModels.size()];
            for (int i = 0; i < videoModels.size(); i++)
            {
                items[i] = videoModels.get(i).getQuantity();
            }
            new AlertDialog.Builder(this)
                    .setSingleChoiceItems(items, 0, null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            dialog.dismiss();
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            if (videoModels.get(selectedPosition).getHost_name().equals(Configs.HOST_NAME.VIMEO)
                                    || videoModels.get(selectedPosition).getHost_name().equals(Configs.HOST_NAME.DAILYMOON)
                                    || videoModels.get(selectedPosition).getHost_name().equals(Configs.HOST_NAME.FACEBOOK))
                            {
                                DownloadEvent downloadEvent = new DownloadEvent(videoModels.get(selectedPosition));
                                EventBus.getDefault().post(downloadEvent);
                                Toast.makeText(VideoDetailActivity.this, "Download processing...!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(VideoDetailActivity.this, "We disable action download video from youtube because due to YouTube Terms of Service", Toast.LENGTH_LONG).show();
                            }
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void prepare()
    {
        progressBar.setVisibility(View.VISIBLE);
        rlContainer.setClickable(false);
        rlContainer.setAlpha(0.5f);
    }

    @Override
    public void complete(List<VideoModel> videoModels)
    {
        progressBar.setVisibility(View.GONE);
        this.videoModels = videoModels;
        rlContainer.setClickable(true);
        rlContainer.setAlpha(1f);
    }

//    @Override
//    public void complete(PageVideosInfo pageVideosInfo)
//    {
//
//    }
}
