package com.trungpt.downloadmaster.download;

import android.graphics.Bitmap;
import com.trungpt.downloadmaster.utils.Configs;

import java.net.URL;

/**
 * Created by Trung on 10/31/2015.
 */
public class VideoModel
{
    private URL url;
    private String urlIcon;
    private String title;
    private String type;
    private String quantity;
    private Bitmap bm;
    private Configs.HOST_NAME host_name;

    public URL getUrl()
    {
        return url;
    }

    public void setUrl(URL url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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

    public String getUrlIcon()
    {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon)
    {
        this.urlIcon = urlIcon;
    }

    public Bitmap getBm()
    {
        return bm;
    }

    public void setBm(Bitmap bm)
    {
        this.bm = bm;
    }

    public Configs.HOST_NAME getHost_name()
    {
        return host_name;
    }

    public void setHost_name(Configs.HOST_NAME host_name)
    {
        this.host_name = host_name;
    }
}
