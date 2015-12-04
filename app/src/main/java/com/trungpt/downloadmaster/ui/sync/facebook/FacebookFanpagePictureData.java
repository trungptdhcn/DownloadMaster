package com.trungpt.downloadmaster.ui.sync.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookFanpagePictureData
{
    @SerializedName("is_silhouette")
    private boolean isSilhouette;
    private String url;

    public boolean isSilhouette()
    {
        return isSilhouette;
    }

    public void setSilhouette(boolean isSilhouette)
    {
        this.isSilhouette = isSilhouette;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
