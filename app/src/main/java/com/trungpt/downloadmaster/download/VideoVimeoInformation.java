package com.trungpt.downloadmaster.download;


import com.trungpt.downloadmaster.download.parser.VimeoInfo;
import com.trungpt.downloadmaster.download.parser.VimeoParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 10/31/2015.
 */
public class VideoVimeoInformation
{
    private List<VimeoParser.VideoDownload> list = new ArrayList<>();
    private VimeoInfo vimeoInfo;

    public VideoVimeoInformation(List<VimeoParser.VideoDownload> list, VimeoInfo vimeoInfo)
    {
        this.list = list;
        this.vimeoInfo = vimeoInfo;
    }

    public List<VimeoParser.VideoDownload> getList()
    {
        return list;
    }

    public void setList(List<VimeoParser.VideoDownload> list)
    {
        this.list = list;
    }

    public VimeoInfo getVimeoInfo()
    {
        return vimeoInfo;
    }

    public void setVimeoInfo(VimeoInfo vimeoInfo)
    {
        this.vimeoInfo = vimeoInfo;
    }
}
