package ru.vuchobe.util.threadUtil;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Реализация ассинхроного выполнения
 */
public class ThreadTaskImpl implements Runnable, ThreadTask {
    @NonNull
    Handler handle;
    @Nullable
    ThreadLooper threadLooper;
    @NonNull
    ThreadTask task;
    ThreadService.Unique unique = ThreadService.Unique.NONE;
    int uniqueNum = ThreadService.NONE;
    int timeStart = ThreadService.NONE;
    int timeReplay = ThreadService.NONE;
    int count = ThreadService.NONE;
    ThreadService.Status status = ThreadService.Status.NONE;
    long progress = 0;
    long progressMax = 0;

    private boolean isStop = false;

    public ThreadTaskImpl() {
    }

    public void work() {
        task.work();
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                if (getStatus() == ThreadService.Status.STOP) {
                    count++;
                    return;
                }
                status = ThreadService.Status.RUN;
                ThreadService.threadLooperByThread.set(this);
            }
            work();
        } catch (Throwable ex) {
            synchronized (this) {
                status = ThreadService.Status.ERROR;
                Log.e("ThreadTask Error", "Run Error", ex);
            }
        } finally {
            synchronized (this) {
                ThreadService.threadLooperByThread.set(null);
                if ((--count) > 0) {
                    if (handle.postDelayed(this, timeStart)) {
                        status = ThreadService.Status.QUEUE;
                    } else {
                        status = ThreadService.Status.STOP;
                    }
                } else if (status != ThreadService.Status.ERROR) {
                    status = ThreadService.Status.DONE;
                }
            }
        }
    }

    synchronized void _start() {
        if (threadLooper == null || !threadLooper.isWorks()) {
            Log.e("ThreadTask error", "Start task in close Handel!!!", new IllegalAccessException());
            return;
        }
        if (count == 0) {
            Log.e("ThreadTask error", "Start task with count = 0", new IllegalAccessError());
            return;
        }
        if (timeStart < 0) timeStart = 0;
        if (timeReplay < 0) timeReplay = 0;
        if (count == ThreadService.NONE) count = 1;
        isStop = false;
        switch (unique) {
            case OLD:
                //Not release
            case NEW:
                //Not release
            case NONE: {
                if (handle.postDelayed(this, timeStart)) {
                    status = ThreadService.Status.QUEUE;
                } else {
                    status = ThreadService.Status.STOP;
                }
            }
        }
    }

    public synchronized void start() {
        isStop = false;
        if (count > 0 && status == ThreadService.Status.STOP) {
            switch (unique) {
                case OLD:
                    //Not release
                case NEW:
                    //Not release
                case NONE: {
                    if (handle.postDelayed(this, timeStart)) {
                        status = ThreadService.Status.QUEUE;
                    } else {
                        status = ThreadService.Status.STOP;
                    }
                }
            }
        }
    }

    public synchronized void stop() {
        isStop = true;
        handle.removeCallbacks(this);
        status = ThreadService.Status.STOP;
    }

    @NonNull
    protected Handler getHandle() {
        return handle;
    }

    @Nullable
    public ThreadLooper getThreadLooper() {
        return threadLooper;
    }

    public ThreadService.Unique getUnique() {
        return unique;
    }

    public int getUniqueNum() {
        return uniqueNum;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeReplay() {
        return timeReplay;
    }

    public int getCount() {
        return count;
    }

    public ThreadService.Status getStatus() {
        if (isStop || !threadLooper.isWorks()) {
            status = ThreadService.Status.STOP;
            handle.removeCallbacks(this);
        }
        if (status == ThreadService.Status.RUN && ThreadService.threadLooperByThread.get() != threadLooper) {
            status = ThreadService.Status.STOP;
            handle.removeCallbacks(this);
        }
        return status;
    }

    @Override
    public void addProgress(long add) {
        setProgress(progress + add);
    }

    public long getProgress() {
        return progress;
    }

    public long getProgressMax() {
        return progressMax;
    }

    public void setProgress(long progress) {
        if (progress >= 0 && progress <= progressMax) {
            this.progress = progress;
            ThreadProgressListener listener = progressListener;
            if (listener != null) listener.onUpdateProgress(this.progress, this.progressMax);
        }
    }

    public void setProgressMax(long progressMax) {
        if (progressMax >= 0) {
            this.progressMax = progressMax;
            if (progress > this.progressMax) progress = this.progressMax;
            ThreadProgressListener listener = progressListener;
            if (listener != null) listener.onUpdateProgress(this.progress, this.progressMax);
        }
    }

    @Override
    public void _init() {
    }

    @Override
    public void _deinit() {
    }

    @NonNull
    @Override
    public ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncMain(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncIO(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return threadLooper.asyncNetwork(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public boolean isWorks() {
        return getStatus() == ThreadService.Status.RUN;
    }

    @Override
    public @Nullable
    Context getContext() {
        return threadLooper.getContext();
    }

    private ThreadProgressListener progressListener;

    @Override
    public void onThreadProgressListener(ThreadProgressListener progressListener) {
        this.progressListener = progressListener;
    }
}