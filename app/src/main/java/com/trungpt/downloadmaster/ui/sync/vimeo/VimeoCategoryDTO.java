package com.trungpt.downloadmaster.ui.sync.vimeo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/13/2015.
 */
public class VimeoCategoryDTO
{
    private String uri;
    private String name;
    private String link;
    private Boolean top_level;
    @SerializedName("pictures")
    private VimeoPicturesDTO vimeoPicturesDTO;

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public Boolean getTop_level()
    {
        return top_level;
    }

    public void setTop_level(Boolean top_level)
    {
        this.top_level = top_level;
    }

    public VimeoPicturesDTO getVimeoPicturesDTO()
    {
        return vimeoPicturesDTO;
    }

    public void setVimeoPicturesDTO(VimeoPicturesDTO vimeoPicturesDTO)
    {
        this.vimeoPicturesDTO = vimeoPicturesDTO;
    }
}
