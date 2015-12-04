package com.trungpt.downloadmaster.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.trungpt.downloadmaster.MainActivity;
import com.trungpt.downloadmaster.R;
import com.trungpt.downloadmaster.common.BaseFragment;
import com.trungpt.downloadmaster.common.event.DownloadEvent;
import com.trungpt.downloadmaster.download.*;
import de.greenrobot.event.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Trung on 11/11/2015.
 */
public class TaskFragment extends BaseFragment
{
    @Bind(R.id.task_fragment_llContainer)
    LinearLayout llContainer;
    private static List<DownloadFileFromURLThread> runnables = new ArrayList<>();
    private BlockingQueue<Runnable> mDecodeWorkQueue;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    ThreadPoolExecutor mDecodeThreadPool;
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    private DownloadTask mDownloadThread;
    @Override
    public int getLayout()
    {
        return R.layout.task_fragment;
    }

    @Override
    public void setDataToView(Bundle savedInstanceState)
    {

        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
        mDecodeThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDecodeWorkQueue);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(final DownloadEvent event)
    {
        ((MainActivity)getActivity()).otherFragment(2);
        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View viewProgress = vi.inflate(R.layout.progress_bar, null, false);
        final TextView tvPercentage = (TextView) viewProgress.findViewById(R.id.progress_bar_tvPercentage);
        final ImageButton btCancel = (ImageButton) viewProgress.findViewById(R.id.progress_bar_btCancel);
        final TextView tvName = (TextView) viewProgress.findViewById(R.id.progress_bar_tvName);
        final TextView tvTitle = (TextView) viewProgress.findViewById(R.id.progress_tvTitle);
        final ImageView ivAvatar = (ImageView) viewProgress.findViewById(R.id.progress_ivAvatar);
        final ProgressBar progressBar = (ProgressBar) viewProgress.findViewById(R.id.progress_bar_progressBar);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 10);
        llContainer.addView(viewProgress, layoutParams);
        final VideoModel videoModel = event.getVideoModel();
        tvTitle.setText(videoModel.getTitle());
//        Glide.with(this)
//                .load(videoModel.getUrlIcon())
//                .centerCrop()
//                .placeholder(R.drawable.no_thumbnail)
//                .crossFade()
//                .into(ivAvatar);
        Glide.with(this)
                .load(videoModel.getUrlIcon())
                .asBitmap()
                .animate(R.anim.abc_fade_in)
                .placeholder(R.drawable.avata_defaut)
                .fallback(R.drawable.avata_defaut)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(150, 150)
                {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim)
                    {
                        ivAvatar.setImageBitmap(bitmap);
                    }
                });
        mDownloadThread = DownloadManager.getInstance().startDownload(event.getVideoModel(),new UpdateProgress()
        {
            @Override
            public void updateProgress(final int percentage)
            {
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressBar.setProgress((int) percentage);
                        if (percentage != 100)
                        {
                            tvPercentage.setText(percentage + " %");
                        }
                        else
                        {
                            tvPercentage.setText("Download complete!");
                        }
                        tvName.setText(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + videoModel.getTitle());
                    }
                });
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                llContainer.removeView(viewProgress);
                DownloadManager.removeDownload(mDownloadThread,videoModel.getUrl());
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + videoModel.getTitle() + "." + videoModel.getType());
                boolean isDelete = file.delete();
            }
        });
    }
}
