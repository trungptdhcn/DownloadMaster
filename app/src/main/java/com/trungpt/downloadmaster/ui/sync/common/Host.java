package com.trungpt.downloadmaster.ui.sync.common;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Trung on 11/19/2015.
 */
public interface Host
{
    public String downloadWebpage(String url) throws IOException;
    public HostDirect extractHostDirect(String url) throws Exception;
}
