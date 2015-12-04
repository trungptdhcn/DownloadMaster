package com.trungpt.downloadmaster.ui.sync.dailymotion;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionDirect
{
    @SerializedName("metadata")
    private DailymotionDirectMetaData dailymotionDirectMetaData;

    public DailymotionDirectMetaData getDailymotionDirectMetaData()
    {
        return dailymotionDirectMetaData;
    }

    public void setDailymotionDirectMetaData(DailymotionDirectMetaData dailymotionDirectMetaData)
    {
        this.dailymotionDirectMetaData = dailymotionDirectMetaData;
    }
}
