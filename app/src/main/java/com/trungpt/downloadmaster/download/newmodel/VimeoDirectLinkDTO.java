package com.trungpt.downloadmaster.download.newmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/16/2015.
 */
public class VimeoDirectLinkDTO
{
    @SerializedName("request")
    private VimeoDirectLinkRequestDTO vimeoDirectLinkRequestDTO;
    @SerializedName("video")
    private VimeoDirectLinkVideoDTO vimeoDirectLinkVideoDTO;

    public VimeoDirectLinkRequestDTO getVimeoDirectLinkRequestDTO()
    {
        return vimeoDirectLinkRequestDTO;
    }

    public void setVimeoDirectLinkRequestDTO(VimeoDirectLinkRequestDTO vimeoDirectLinkRequestDTO)
    {
        this.vimeoDirectLinkRequestDTO = vimeoDirectLinkRequestDTO;
    }

    public VimeoDirectLinkVideoDTO getVimeoDirectLinkVideoDTO()
    {
        return vimeoDirectLinkVideoDTO;
    }

    public void setVimeoDirectLinkVideoDTO(VimeoDirectLinkVideoDTO vimeoDirectLinkVideoDTO)
    {
        this.vimeoDirectLinkVideoDTO = vimeoDirectLinkVideoDTO;
    }
}
