package com.trungpt.downloadmaster.download.newmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/16/2015.
 */
public class VimeoDirectLinkRequestDTO
{
    @SerializedName("files")
    private VimeoDirectLinkFilesDTO vimeoDirectLinkFilesDTO;

    public VimeoDirectLinkFilesDTO getVimeoDirectLinkFilesDTO()
    {
        return vimeoDirectLinkFilesDTO;
    }

    public void setVimeoDirectLinkFilesDTO(VimeoDirectLinkFilesDTO vimeoDirectLinkFilesDTO)
    {
        this.vimeoDirectLinkFilesDTO = vimeoDirectLinkFilesDTO;
    }
}
