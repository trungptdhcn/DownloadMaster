package com.trungpt.downloadmaster.ui.listener;

import com.trungpt.downloadmaster.ui.model.VideoItem;

import java.util.List;

/**
 * Created by Trung on 11/12/2015.
 */
public interface AsyncTaskLoadMoreListener
{
    public void prepareLoadMore();
    public void completeLoadMore(List<VideoItem> videoItems);
}
