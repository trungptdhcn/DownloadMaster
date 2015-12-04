package com.trungpt.downloadmaster.ui.sync.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookFanpagePicture
{
    @SerializedName("data")
    private FacebookFanpagePictureData fanpagePictureData;

    public FacebookFanpagePictureData getFanpagePictureData()
    {
        return fanpagePictureData;
    }

    public void setFanpagePictureData(FacebookFanpagePictureData fanpagePictureData)
    {
        this.fanpagePictureData = fanpagePictureData;
    }
}
