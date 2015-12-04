package com.trungpt.downloadmaster.common.event;


import com.trungpt.downloadmaster.download.VideoModel;

/**
 * Created by Trung on 10/27/2015.
 */
public class DownloadEvent
{
    private VideoModel videoModel;

    public DownloadEvent(VideoModel videoModel)
    {
        this.videoModel = videoModel;
    }

    public VideoModel getVideoModel()
    {
        return videoModel;
    }

    public void setVideoModel(VideoModel videoModel)
    {
        this.videoModel = videoModel;
    }
}
