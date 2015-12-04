package com.trungpt.downloadmaster.download.parser;


import com.trungpt.downloadmaster.download.info.VideoInfo;

import java.net.URL;


public class VimeoInfo extends VideoInfo
{

    // keep it in order hi->lo
    public enum VimeoQuality
    {
        pHi, pLow
    }

    private VimeoQuality vq;

    public VimeoInfo(URL web)
    {
        super(web);
    }

    public VimeoQuality getVideoQuality()
    {
        return vq;
    }

    public void setVideoQuality(VimeoQuality vq)
    {
        this.vq = vq;
    }

}