package com.trungpt.downloadmaster.ui.sync.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookFanpagePaging
{
    private String next;
    @SerializedName("cursors")
    private FacebookFanpagePagingCursor cursors;


    public String getNext()
    {
        return next;
    }

    public void setNext(String next)
    {
        this.next = next;
    }

    public FacebookFanpagePagingCursor getCursors()
    {
        return cursors;
    }

    public void setCursors(FacebookFanpagePagingCursor cursors)
    {
        this.cursors = cursors;
    }
}
