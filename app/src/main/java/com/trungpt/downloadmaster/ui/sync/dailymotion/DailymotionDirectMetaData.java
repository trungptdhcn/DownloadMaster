package com.trungpt.downloadmaster.ui.sync.dailymotion;

import com.google.gson.annotations.SerializedName;
import com.trungpt.downloadmaster.ui.sync.common.HostDirect;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionDirectMetaData extends HostDirect
{
    private Long duration;
    private String id;
    private String title;
    private String poster_url;
    @SerializedName("qualities")
    private DailymotionDirectQuatities dailymotionDirectQuatities;

    public Long getDuration()
    {
        return duration;
    }

    public void setDuration(Long duration)
    {
        this.duration = duration;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPoster_url()
    {
        return poster_url;
    }

    public void setPoster_url(String poster_url)
    {
        this.poster_url = poster_url;
    }

    public DailymotionDirectQuatities getDailymotionDirectQuatities()
    {
        return dailymotionDirectQuatities;
    }

    public void setDailymotionDirectQuatities(DailymotionDirectQuatities dailymotionDirectQuatities)
    {
        this.dailymotionDirectQuatities = dailymotionDirectQuatities;
    }
}
