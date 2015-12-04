package com.trungpt.downloadmaster.ui.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskListener;
import com.trungpt.downloadmaster.ui.model.PageVideosInfo;
import com.trungpt.downloadmaster.ui.sync.dailymotion.DailymotionConnector;
import com.trungpt.downloadmaster.ui.sync.vimeo.VimeoConnector;
import com.trungpt.downloadmaster.ui.sync.youtube.YoutubeConnector;
import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 11/12/2015.
 */
public class AsyncTaskGetVideo extends AsyncTask<Void, Void, PageVideosInfo>
{
    AsyncTaskListener listener;
    private Configs.HOST_NAME host_name;
    YoutubeConnector youtubeConnector;
    VimeoConnector vimeoConnector;
    DailymotionConnector  dailymotionConnector;
    Context context;
    String regionCode;


    public AsyncTaskGetVideo(Context context, Configs.HOST_NAME host_name, String regionCode)
    {
        this.host_name = host_name;
        this.context = context;
        this.regionCode = regionCode;
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
        switch (host_name)
        {
            case YOUTUBE:
                pageVideosInfo = youtubeConnector.mostPopular(regionCode);
                break;
            case VIMEO:
//                VimeoCategoriesDTO vimeoCategoriesDTO = vimeoConnector.getCategories();
//                String uri = vimeoCategoriesDTO.getVimeoCategoryDTOs().get(0).getUri();
//                String categoryId = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
                pageVideosInfo = vimeoConnector.getVideosOfCategory(regionCode);
//                pageVideosInfo.setOtherData(vimeoCategoriesDTO);
                break;
            case DAILYMOON:
                pageVideosInfo = dailymotionConnector.getVideoOfChannel(regionCode);
                break;
            case FACEBOOK:
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
