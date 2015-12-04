package com.trungpt.downloadmaster.ui.sync.vimeo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/13/2015.
 */
public class VimeoInfoDTO
{
    @SerializedName("paging")
    private VimeoPaging vimeoPaging;
    @SerializedName("data")
    private List<VimeoDTO> vimeoDTOList = new ArrayList<>();

    public VimeoPaging getVimeoPaging()
    {
        return vimeoPaging;
    }

    public void setVimeoPaging(VimeoPaging vimeoPaging)
    {
        this.vimeoPaging = vimeoPaging;
    }

    public List<VimeoDTO> getVimeoDTOList()
    {
        return vimeoDTOList;
    }

    public void setVimeoDTOList(List<VimeoDTO> vimeoDTOList)
    {
        this.vimeoDTOList = vimeoDTOList;
    }
}
