package com.trungpt.downloadmaster.ui;

import android.os.Bundle;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/18/2015.
 */
public final class YoutubeVideoFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener
{
    private YouTubePlayer player;
    private String videoId;

    public static YoutubeVideoFragment newInstance()
    {
        return new YoutubeVideoFragment();
    }

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        initialize(Configs.YOUTUBE_KEY, this);
    }

    @Override
    public void onDestroy()
    {
        if (player != null)
        {
            player.release();
        }
        super.onDestroy();
    }

    public void pause()
    {
        if (player != null)
        {
            player.pause();
        }
    }

    public void setVideoId(String videoId)
    {
        if (videoId != null && !videoId.equals(this.videoId))
        {
            this.videoId = videoId;
            if (player != null)
            {
                player.cueVideo(videoId);
            }
        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
    {
        this.player = youTubePlayer;
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
//        player.setOnFullscreenListener((VideoListDemoActivity) getActivity());
//        if (!restored && videoId != null)
//        {
//            player.cueVideo(videoId);
//        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
        this.player = null;
    }
}
