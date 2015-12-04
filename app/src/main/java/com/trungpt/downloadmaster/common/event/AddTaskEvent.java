package com.trungpt.downloadmaster.common.event;

import com.trungpt.downloadmaster.download.VideoModel;

/**
 * Created by Trung on 11/16/2015.
 */
public class AddTaskEvent
{
    private VideoModel videoModel;

    public AddTaskEvent(VideoModel videoModel)
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
