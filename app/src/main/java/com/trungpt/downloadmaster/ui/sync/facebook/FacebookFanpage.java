package com.trungpt.downloadmaster.ui.sync.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/22/2015.
 */
public class FacebookFanpage
{
    private String id;
    private String name;
    @SerializedName("picture")
    private FacebookFanpagePicture facebookFanpagePicture;
    private String description;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public FacebookFanpagePicture getFacebookFanpagePicture()
    {
        return facebookFanpagePicture;
    }

    public void setFacebookFanpagePicture(FacebookFanpagePicture facebookFanpagePicture)
    {
        this.facebookFanpagePicture = facebookFanpagePicture;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
