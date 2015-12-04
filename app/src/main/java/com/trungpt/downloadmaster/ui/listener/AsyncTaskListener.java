package com.trungpt.downloadmaster.ui.listener;

import com.trungpt.downloadmaster.ui.model.PageVideosInfo;

/**
 * Created by Trung on 11/12/2015.
 */
public interface AsyncTaskListener
{
    public void prepare();
    public void complete(PageVideosInfo pageVideosInfo);

}
