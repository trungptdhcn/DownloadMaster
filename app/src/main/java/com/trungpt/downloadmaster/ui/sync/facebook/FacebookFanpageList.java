package com.trungpt.downloadmaster.ui.sync.facebook;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookFanpageList
{
    @SerializedName("data")
List<FacebookFanpage> facebookFanpageList = new ArrayList<>();
    FacebookFanpagePaging paging;
    public List<FacebookFanpage> getFacebookFanpageList()
    {
        return facebookFanpageList;
    }

    public void setFacebookFanpageList(List<FacebookFanpage> facebookFanpageList)
    {
        this.facebookFanpageList = facebookFanpageList;
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
