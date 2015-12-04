package com.trungpt.downloadmaster.download;

import android.os.Environment;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Trung on 10/31/2015.
 */
public abstract class DownloadFileFromURLThread implements Runnable
{
    private String urlStr;
    private String type;
    private String tile;
    private volatile boolean isComplete = false;

    protected DownloadFileFromURLThread(String urlStr, String type, String title)
    {
        this.urlStr = urlStr;
        this.type = type;
        this.tile = title;
    }

    @Override
    public void run()
    {
        int count;
        try
        {
            while (!Thread.currentThread().isInterrupted() && !isComplete)
            {
                URL url = new URL(urlStr);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File file = createNameFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + this.tile, type);
                OutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                long start = System.currentTimeMillis();
                long total = 0;
                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    output.write(data, 0, count);
                    updateProgress((int) ((total * 100) / lenghtOfFile));
                }
                output.flush();
                output.close();
                input.close();
                isComplete = true;
            }
        }
        catch (Exception e)
        {
            isComplete = false;
        }
    }

    public File createNameFile(String name, String type)
    {
        File file = new File(name + "." + type);
        int k = 1;
        if (!file.exists())
        {
            return file;
        }
        else
        {
            file = createNameFile(name + "(" + k + ")", type);
            k++;
        }
        return file;
    }

    public abstract void updateProgress(long percentage);



}
