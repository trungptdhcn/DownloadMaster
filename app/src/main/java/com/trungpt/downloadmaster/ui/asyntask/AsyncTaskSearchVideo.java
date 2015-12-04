package com.trungpt.downloadmaster.ui.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskListener;
import com.trungpt.downloadmaster.ui.model.ModelInfo;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.model.VimeoInfo;
import com.trungpt.downloadmaster.ui.model.YoutubeInfo;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionConnector;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionInfo;
import com.trungpt.downloadmaster.ui.sync.vimeo.VimeoConnector;
import com.trungpt.downloadmaster.ui.sync.youtube.YoutubeConnector;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/12/2015.
 */
public class AsyncTaskSearchVideo extends AsyncTask<Void, Void, PageVideosInfo>
{
    AsyncTaskListener listener;
    private Configs.SEARCH_MODE search_mode;
    YoutubeConnector youtubeConnector;
    VimeoConnector vimeoConnector;
    DailymotionConnector dailymotionConnector;
    Context context;
    ModelInfo modelInfo;

    public AsyncTaskSearchVideo(Context context, Configs.SEARCH_MODE search_mode, ModelInfo modelInfo)
    {
        this.search_mode = search_mode;
        this.context = context;
        this.modelInfo = modelInfo;
        youtubeConnector = new YoutubeConnector(context);
        vimeoConnector = new VimeoConnector();
        dailymotionConnector = new DailymotionConnector();
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        listener.prepare();
    }


    @Override
    protected PageVideosInfo doInBackground(Void... params)
    {
        PageVideosInfo pageVideosInfo = null;
        switch (search_mode)
        {
            case VIMEO:
                pageVideosInfo = vimeoConnector.search((VimeoInfo) modelInfo);
                break;
            case YOUTUBE:
                pageVideosInfo = youtubeConnector.search((YoutubeInfo) modelInfo);
                break;
            case DAILYMOTION:
                pageVideosInfo = dailymotionConnector.search((DailymotionInfo)modelInfo);
                break;
        }
        return pageVideosInfo;
    }

    @Override
    protected void onPostExecute(PageVideosInfo pageVideosInfo)
    {
        super.onPostExecute(pageVideosInfo);
        listener.complete(pageVideosInfo);
    }

    public void setListener(AsyncTaskListener listener)
    {
        this.listener = listener;
    }
}
