package com.trungpt.downloadmaster.download.newmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/16/2015.
 */
public class VimeoDirectLinkProgressiveDTO
{
    @SerializedName("url")
    private String url;
    @SerializedName("quality")
    private String quality;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getQuality()
    {
        return quality;
    }

    public void setQuality(String quality)
    {
        this.quality = quality;
    }
}
