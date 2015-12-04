package com.trungpt.downloadmaster.download.newmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/16/2015.
 */
public class VimeoDirectLinkFilesDTO
{
    @SerializedName("progressive")
    private List<VimeoDirectLinkProgressiveDTO> vimeoDirectLinkProgressiveDTO = new ArrayList<>();

    public List<VimeoDirectLinkProgressiveDTO> getVimeoDirectLinkProgressiveDTO()
    {
        return vimeoDirectLinkProgressiveDTO;
    }

    public void setVimeoDirectLinkProgressiveDTO(List<VimeoDirectLinkProgressiveDTO> vimeoDirectLinkProgressiveDTO)
    {
        this.vimeoDirectLinkProgressiveDTO = vimeoDirectLinkProgressiveDTO;
    }
}
