package com.trungpt.downloadmaster.download;

import java.net.URL;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Trung on 11/6/2015.
 * This class to creates pools of background
 * The class set pool size on particular operation
 * Class use two threadpool in order to limit the number of simulator
 */
public class DownloadManager
{
    // Sets the amount of time an idle thread will wait for a task before terminating
    private static final int KEEP_ALIVE_TIME = 1;

    //Set the Time Unit to second
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;

    //Set the initial threadpool size to 8
    private static final int CORE_POOL_SIZE = 5;

    // Set maximum threadpool size to 8
    private static final int MAXIMUM_POOL_SIZE = 5;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // A queue of Runnables for the image download pool
    private final BlockingQueue<Runnable> downloadQueue;
    // A managed pool of background download threads
    private final Queue<DownloadTask> downloadTaskQueue;

    // A managed pool of background download threads
    private final ThreadPoolExecutor downloadThreadPool;

    // Singleton instance of DownloadManager, implement singleton pattern
    private static DownloadManager instance = null;

    // A static block that sets class fields

    static
    {

        // The time unit for "keep alive" is in seconds
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        // Creates a single static instance of PhotoManager
        instance = new DownloadManager();
    }

    private DownloadManager()
    {
        /*
        *Creates a work queue for the pool of thread,
        * using a linked lest queue that block when the queue is empty
         */
        downloadQueue = new LinkedBlockingDeque<>();
        downloadTaskQueue = new LinkedBlockingDeque<>();
        downloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, downloadQueue);

    }

    public static DownloadManager getInstance()
    {
        return instance;
    }

    /**
     * Cancel all Threads in Thread Pool
     */
    public static void cancelAll()
    {
        DownloadTask downloadTaskArray[] = new DownloadTask[instance.downloadTaskQueue.size()];
        instance.downloadTaskQueue.toArray(downloadTaskArray);
        int taskArraylen = downloadTaskArray.length;

        synchronized (instance)
        {

            // Iterates over the array of tasks
            for (int taskArrayIndex = 0; taskArrayIndex < taskArraylen; taskArrayIndex++)
            {

                // Gets the task's current thread
                Thread thread = downloadTaskArray[taskArrayIndex].threadInside;

                // if the Thread exists, post an interrupt to it
                if (null != thread)
                {
                    thread.interrupt();
                }
            }
        }
    }

    public static void removeDownload(DownloadTask downloadTask, URL videoURL)
    {
        if (downloadTask != null && downloadTask.getVideoModel().getUrl().equals(videoURL))
        {
            synchronized (instance)
            {
                Thread thread = downloadTask.getCurrentThread();
                if (null != thread)
                {
                    thread.interrupt();
                }
            }
            instance.downloadThreadPool.remove(downloadTask.getDownloadRunnable());
        }
    }

    public static DownloadTask startDownload(VideoModel videoModel,UpdateProgress updateProgress)
    {
        DownloadTask downloadTask = instance.downloadTaskQueue.poll();
        if (null == downloadTask)
        {
            downloadTask = new DownloadTask(updateProgress);
        }
        downloadTask.initializeDownloaderTask(DownloadManager.instance,videoModel,updateProgress);
        instance.downloadThreadPool.execute(downloadTask.getDownloadRunnable());
        return downloadTask;
    }

//    /**
//     * Recycles tasks by calling their internal recycle() method and then putting them back into
//     * the task queue.
//     *
//     * @param downloadTask The task to recycle
//     */
//    void recycleTask(DownloadTask downloadTask)
//    {
//
//        // Frees up memory in the task
//        downloadTask.recycle();
//
//        // Puts the task object back into the queue for re-use.
//        mPhotoTaskWorkQueue.offer(downloadTask);
//    }
}
