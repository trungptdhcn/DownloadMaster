package com.trungpt.downloadmaster.download.newmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/16/2015.
 */
public class VimeoDirectLinkVideoDTO
{
    @SerializedName("thumbs")
    private VimeoDirectLinkThumbDTO vimeoDirectLinkThumbDTO;
    private String title;
    public VimeoDirectLinkThumbDTO getVimeoDirectLinkThumbDTO()
    {
        return vimeoDirectLinkThumbDTO;
    }

    public void setVimeoDirectLinkThumbDTO(VimeoDirectLinkThumbDTO vimeoDirectLinkThumbDTO)
    {
        this.vimeoDirectLinkThumbDTO = vimeoDirectLinkThumbDTO;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
