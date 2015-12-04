package com.trungpt.downloadmaster.ui.sync.vimeo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/13/2015.
 */
public class VimeoCategoriesDTO
{
    private VimeoPaging paging;
    @SerializedName("data")
    private List<VimeoCategoryDTO> vimeoCategoryDTOs = new ArrayList<>();

    public VimeoPaging getPaging()
    {
        return paging;
    }

    public void setPaging(VimeoPaging paging)
    {
        this.paging = paging;
    }

    public List<VimeoCategoryDTO> getVimeoCategoryDTOs()
    {
        return vimeoCategoryDTOs;
    }

    public void setVimeoCategoryDTOs(List<VimeoCategoryDTO> vimeoCategoryDTOs)
    {
        this.vimeoCategoryDTOs = vimeoCategoryDTOs;
    }
}
