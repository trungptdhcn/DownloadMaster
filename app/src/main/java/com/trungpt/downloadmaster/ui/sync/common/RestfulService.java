package com.trungpt.downloadmaster.ui.sync.common;

import com.trungpt.downloadmaster.utils.Configs;

/**
 * Created by Trung on 7/22/2015.
 */
public class RestfulService
{
    private static RestfulServiceIn restfulServiceIn;

    public static RestfulServiceIn getInstance(Configs.HOST_NAME host_name)
    {
        restfulServiceIn = RestfulAdapter.getRestAdapter(host_name).create(RestfulServiceIn.class);
        return restfulServiceIn;
    }
}
