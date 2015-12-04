package com.trungpt.downloadmaster.ui.sync.vimeo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Trung on 11/13/2015.
 */
public class VimeoPicturesDTO
{
    private String uri;
    private Boolean active;
    private String type;
    @SerializedName("sizes")
    private List<VimeoPicturesSizeDTO> vimeoPicturesSizeDTO;

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public List<VimeoPicturesSizeDTO> getVimeoPicturesSizeDTO()
    {
        return vimeoPicturesSizeDTO;
    }

    public void setVimeoPicturesSizeDTO(List<VimeoPicturesSizeDTO> vimeoPicturesSizeDTO)
    {
        this.vimeoPicturesSizeDTO = vimeoPicturesSizeDTO;
    }
}
