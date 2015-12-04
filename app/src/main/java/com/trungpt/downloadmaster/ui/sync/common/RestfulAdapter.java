package com.trungpt.downloadmaster.ui.sync.common;

import com.squareup.okhttp.OkHttpClient;
import com.trungpt.downloadmaster.utils.Configs;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by Trung on 7/22/2015.
 */
public class RestfulAdapter
{
    private static RestAdapter restAdapter;

    public static RestAdapter getRestAdapter(Configs.HOST_NAME host_name)
    {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(6000, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(6000, TimeUnit.SECONDS);
//        okHttpClient.interceptors().add(new Interceptor()
//        {
//            @Override
//            public Response intercept(Chain chain) throws IOException
//            {
//                Request request = chain.request();
//
//                // try the request
//                Response response = chain.proceed(request);
//
//                int tryCount = 0;
//                while (!response.isSuccessful() && tryCount < 3)
//                {
//                    tryCount++;
//                    response = chain.proceed(request);
//                }
//                return response;
//            }
//        });
        switch (host_name)
        {
            case VIMEO:
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Configs.VIMEO_BASE_URL)
                        .setClient(new OkClient(okHttpClient))
                        .build();
                break;
            case DAILYMOON:
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Configs.DAILYMOTION_BASE_URL)
                        .setClient(new OkClient(okHttpClient))
                        .build();
                break;
            case FACEBOOK:
                break;
            default:
        }
        return restAdapter;
    }
}
