package com.trungpt.downloadmaster.ui.sync.dailymotion;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionDetailDTO
{
    private String channel;
    private String country;
    private String description;
    private String id;
    private String thumbnail_720_url;
    private String url;
    private String title;
    private String live_publish_url;

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getThumbnail_720_url()
    {
        return thumbnail_720_url;
    }

    public void setThumbnail_720_url(String thumbnail_720_url)
    {
        this.thumbnail_720_url = thumbnail_720_url;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
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

    public String getLive_publish_url()
    {
        return live_publish_url;
    }

    public void setLive_publish_url(String live_publish_url)
    {
        this.live_publish_url = live_publish_url;
    }
}
