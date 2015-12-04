package com.trungpt.downloadmaster.ui.model;

import java.util.List;

/**
 * Created by Trung on 11/12/2015.
 */
public class PageVideosInfo
{
    List<VideoItem> listVideos;
    String nextPageToken;
    Object otherData;
    boolean isNextpage = true;

    public PageVideosInfo(List<VideoItem> listVideos)
    {
        this.listVideos = listVideos;
    }

    public List<VideoItem> getListVideos()
    {
        return listVideos;
    }

    public void setListVideos(List<VideoItem> listVideos)
    {
        this.listVideos = listVideos;
    }

    public String getNextPageToken()
    {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken)
    {
        this.nextPageToken = nextPageToken;
    }

    public Object getOtherData()
    {
        return otherData;
    }

    public void setOtherData(Object otherData)
    {
        this.otherData = otherData;
    }

    public boolean isNextpage()
    {
        return isNextpage;
    }

    public void setNextpage(boolean isNextpage)
    {
        this.isNextpage = isNextpage;
    }
}
