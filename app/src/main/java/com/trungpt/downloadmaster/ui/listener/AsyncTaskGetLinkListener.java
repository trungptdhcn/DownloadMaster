package com.trungpt.downloadmaster.ui.listener;

import com.trungpt.downloadmaster.download.VideoModel;

import java.util.List;

/**
 * Created by Trung on 11/16/2015.
 */
public interface AsyncTaskGetLinkListener
{
    public void prepare();
    public void complete(List<VideoModel> videoModels);
}
