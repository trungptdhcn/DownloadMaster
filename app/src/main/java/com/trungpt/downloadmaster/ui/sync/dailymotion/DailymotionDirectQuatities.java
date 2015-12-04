package com.trungpt.downloadmaster.ui.sync.dailymotion;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung on 11/19/2015.
 */
public class DailymotionDirectQuatities
{
    @SerializedName("auto")
    List<DailymotionDirectQuality> _auto = new ArrayList<>();
    @SerializedName("240")
    List<DailymotionDirectQuality> _240 = new ArrayList<>();
    @SerializedName("380")
    List<DailymotionDirectQuality> _380 = new ArrayList<>();
    @SerializedName("480")
    List<DailymotionDirectQuality> _480 = new ArrayList<>();
    @SerializedName("720")
    List<DailymotionDirectQuality> _720 = new ArrayList<>();

    public List<DailymotionDirectQuality> get_auto()
    {
        return _auto;
    }

    public void set_auto(List<DailymotionDirectQuality> _auto)
    {
        this._auto = _auto;
    }

    public List<DailymotionDirectQuality> get_240()
    {
        return _240;
    }

    public void set_240(List<DailymotionDirectQuality> _240)
    {
        this._240 = _240;
    }

    public List<DailymotionDirectQuality> get_380()
    {
        return _380;
    }

    public void set_380(List<DailymotionDirectQuality> _380)
    {
        this._380 = _380;
    }

    public List<DailymotionDirectQuality> get_480()
    {
        return _480;
    }

    public void set_480(List<DailymotionDirectQuality> _480)
    {
        this._480 = _480;
    }

    public List<DailymotionDirectQuality> get_720()
    {
        return _720;
    }

    public void set_720(List<DailymotionDirectQuality> _720)
    {
        this._720 = _720;
    }
}
