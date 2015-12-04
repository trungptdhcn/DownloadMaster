package com.trungpt.downloadmaster.ui.sync.facebook;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookVideoList
{
    @SerializedName("data")
    private List<FacebookVideo> facebookVideos = new ArrayList<>();
    private FacebookFanpagePaging paging;

    public List<FacebookVideo> getFacebookVideos()
    {
        return facebookVideos;
    }

    public void setFacebookVideos(List<FacebookVideo> facebookVideos)
    {
        this.facebookVideos = facebookVideos;
    }

    public FacebookFanpagePaging getPaging()
    {
        return paging;
    }

    public void setPaging(FacebookFanpagePaging paging)
    {
        this.paging = paging;
    }
}
