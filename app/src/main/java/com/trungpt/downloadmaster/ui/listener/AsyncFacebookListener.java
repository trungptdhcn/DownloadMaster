package com.trungpt.downloadmaster.ui.listener;

import com.trungpt.downloadmaster.ui.model.PageVideosInfo;

import java.util.Objects;

/**
 * Created by Trung on 11/23/2015.
 */
public interface AsyncFacebookListener
{
    public void prepare();
    public void complete(Object obj);
}
