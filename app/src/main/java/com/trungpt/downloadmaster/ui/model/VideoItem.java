package com.trungpt.downloadmaster.ui.model;

import com.trungpt.downloadmaster.utils.Configs;

import java.io.Serializable;

/**
 * Created by Trung on 11/11/2015.
 */
public class VideoItem implements Serializable
{
    private String title;
    private String description;
    private String type;
    private String url;
    private String urlThumbnail;
    private String id;
    private Configs.HOST_NAME host_name;

//    public VideoItem(String title, String type, String url, String urlThumbnail)
//    {
//        this.title = title;
//        this.type = type;
//        this.url = url;
//        this.urlThumbnail = urlThumbnail;
//    }

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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrlThumbnail()
    {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail)
    {
        this.urlThumbnail = urlThumbnail;
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

    public Configs.HOST_NAME getHost_name()
    {
        return host_name;
    }

    public void setHost_name(Configs.HOST_NAME host_name)
    {
        this.host_name = host_name;
    }
}
