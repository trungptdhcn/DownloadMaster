package com.trungpt.downloadmaster.download;

import android.os.Environment;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Trung on 11/6/2015.
 * this class to downloads video from url.
 * Object of this class instantiated and managed by instance of download task
 */
public class VideoDownloadRunnable implements Runnable
{
    final TaskRunnableDownloadMethod videoDownLoadTask;
    UpdateProgress mUpdateInstance;
    private volatile boolean isComplete = false;


    public VideoDownloadRunnable(TaskRunnableDownloadMethod videoDownLoadTask, UpdateProgress updateProgress)
    {
        this.videoDownLoadTask = videoDownLoadTask;
        mUpdateInstance = updateProgress;
    }


    @Override
    public void run()
    {
        int count;
        /**
         * Stores the current thread in the videoDownloadTask instance, so instance can stop  the thread
         */
        videoDownLoadTask.setDownloadThread(Thread.currentThread());
        // Moves current Thread to background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        /**
         * A try block download video from URL. URL is information
         * in VideoModel is field of PhotoTask
         */
        try
        {
            if (Thread.interrupted())
            {
                throw new InterruptedException();
            }
            while (!Thread.currentThread().isInterrupted() && !isComplete)
            {
                URL url = videoDownLoadTask.getVideoModel().getUrl();
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File file = createNameFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"
                        + videoDownLoadTask.getVideoModel().getTitle(), videoDownLoadTask.getVideoModel().getType());
                OutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                long start = System.currentTimeMillis();
                long total = 0;
                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    output.write(data, 0, count);
                    mUpdateInstance.updateProgress((int) ((total * 100) / lenghtOfFile));
                }
                output.flush();
                output.close();
                input.close();
                isComplete = true;
            }

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            isComplete = false;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            isComplete = false;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            isComplete = false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            isComplete = false;
        }


    }

    /**
     * This method to create name of file when download and save to sdCard
     *
     * @param name this is param to gen filename
     * @param type this is type of video when save in sdCard
     * @return
     */
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

//    ===========================Runable method of task===============================

    /**
     * An interface that defines methods that DownloadTask implements.
     */
    interface TaskRunnableDownloadMethod
    {
        /**
         * Sets thread, that this instance running on
         *
         * @param currentThread the current thread
         */
        void setDownloadThread(Thread currentThread);

        /**
         * Gets video information being downloaded
         *
         * @return
         */
        VideoModel getVideoModel();


    }
}
