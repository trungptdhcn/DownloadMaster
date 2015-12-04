package com.trungpt.downloadmaster.download;

import android.graphics.Bitmap;
import com.trungpt.downloadmaster.download.info.VideoInfo;

/**
 * Created by Trung on 11/2/2015.
 */
public class VideoNormalInformation
{
    private String url;
    private VideoInfo info;
    private String type;
    private String quantity;
    private Bitmap bmIcon;

    public VideoNormalInformation(String url, VideoInfo info, String type, String quantity,Bitmap bm)
    {
        this.url = url;
        this.info = info;
        this.type = type;
        this.quantity = quantity;
        this.bmIcon = bm;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public VideoInfo getInfo()
    {
        return info;
    }

    public void setInfo(VideoInfo info)
    {
        this.info = info;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public Bitmap getBmIcon()
    {
        return bmIcon;
    }

    public void setBmIcon(Bitmap bmIcon)
    {
        this.bmIcon = bmIcon;
    }
}
