package com.trungpt.downloadmaster.ui.asyntask;

import android.os.AsyncTask;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.trungpt.downloadmaster.ui.listener.AsyncFacebookListener;
import com.trungpt.downloadmaster.ui.listener.AsyncTaskGetLinkListener;

/**
 * Created by Trung on 11/23/2015.
 */
public class AsyncTaskFacebook extends AsyncTask<Void,GraphResponse,GraphResponse>
{
    private GraphRequest graphRequest;
    AsyncFacebookListener listener;

    public AsyncTaskFacebook(GraphRequest graphRequest)
    {
        this.graphRequest = graphRequest;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        listener.prepare();
    }

    @Override
    protected GraphResponse doInBackground(Void... params)
    {
        return graphRequest.executeAndWait();
    }

    @Override
    protected void onPostExecute(GraphResponse graphResponse)
    {
        super.onPostExecute(graphResponse);
        listener.complete(graphResponse);
    }

    public AsyncFacebookListener getListener()
    {
        return listener;
    }

    public void setListener(AsyncFacebookListener listener)
    {
        this.listener = listener;
    }
}
