package com.trungpt.downloadmaster.download.newmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trung on 11/16/2015.
 */
public class VimeoDirectLinkThumbDTO
{
    @SerializedName("1280")
    private String _1280;
    @SerializedName("960")
    private String _960;
    @SerializedName("640")
    private String _640;
    @SerializedName("base")
    private String _base;

    public String get_1280()
    {
        return _1280;
    }

    public void set_1280(String _1280)
    {
        this._1280 = _1280;
    }

    public String get_960()
    {
        return _960;
    }

    public void set_960(String _960)
    {
        this._960 = _960;
    }

    public String get_640()
    {
        return _640;
    }

    public void set_640(String _640)
    {
        this._640 = _640;
    }

    public String get_base()
    {
        return _base;
    }

    public void set_base(String _base)
    {
        this._base = _base;
    }
}
