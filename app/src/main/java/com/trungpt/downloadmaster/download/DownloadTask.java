package com.trungpt.downloadmaster.download;


/**
 * Created by Trung on 11/6/2015.
 * This class to manages VideoDownloadRunnable object. It does't perform
 * the download, it manages task for work
 * In effect this allows downloadtask to start on a thread, run download in a delegate object and start again
 */
public class DownloadTask implements VideoDownloadRunnable.TaskRunnableDownloadMethod
{
    // The information model of Video
    private VideoModel videoModel;

    /*
      * This thread task running on
      *
     */
    Thread threadInside;
    /*
      * This object reference to runnable object that handle download
     */
    private Runnable downloadRunnable;
    // The thread on which this task is currently running
    private Thread currentThread;
    /*
      * An object that contains the ThreadPool singleton
     */
    private static DownloadManager mDownloadManager;

    /**
     * Create DownloadTask instance, which contains a download runnable object
     */

    /*
    Update progress download video
     */
    UpdateProgress updateProgress;

    public DownloadTask(UpdateProgress updateProgress)
    {
        downloadRunnable = new VideoDownloadRunnable(this,updateProgress);
        mDownloadManager = DownloadManager.getInstance();
    }

    public void initializeDownloaderTask(DownloadManager downloadManager, VideoModel videoModel,UpdateProgress updateProgress)
    {
        mDownloadManager = downloadManager;
        this.videoModel = videoModel;
        this.updateProgress = updateProgress;
    }

    //=================Get & set==================================
    public Runnable getDownloadRunnable()
    {
        return downloadRunnable;
    }

    public Thread getCurrentThread()
    {
        synchronized (mDownloadManager)
        {
            return currentThread;
        }
    }

    public void setCurrentThread(Thread thread)
    {
        synchronized (mDownloadManager)
        {
            currentThread = thread;
        }
    }
    //==============================================================

    @Override
    public void setDownloadThread(Thread currentThread)
    {
        setCurrentThread(currentThread);
    }

    @Override
    public VideoModel getVideoModel()
    {
        return videoModel;
    }
}
