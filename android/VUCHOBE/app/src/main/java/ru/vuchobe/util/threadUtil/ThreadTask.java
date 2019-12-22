package ru.vuchobe.util.threadUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Реализация ассинхроного выполнения
 */
public class ThreadTask implements Runnable, IThreadTask, ThreadLooper {

    @NonNull
    Handler handle;
    @Nullable
    ThreadLooper threadLooper;
    @NonNull
    IThreadTask task;
    ThreadService.Unique unique = ThreadService.Unique.NONE;
    int uniqueNum = ThreadService.NONE;
    int timeStart = ThreadService.NONE;
    int timeReplay = ThreadService.NONE;
    int count = ThreadService.NONE;
    ThreadService.Status status = ThreadService.Status.NONE;
    long progress = 0;
    long progressMax = 0;

    private boolean isStop = false;

    public ThreadTask() {
    }

    @Override
    public void work(ThreadTask threadTask) {
        task.work(threadTask);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                if (getStatus() == ThreadService.Status.STOP) {
                    count++;
                    return;
                }
                unqueNumWork.put(uniqueNum, this);
                status = ThreadService.Status.RUN;
                ThreadService.threadTaskByThread.set(this);
            }
            work(this);
        } catch (Throwable ex) {
            synchronized (this) {
                status = ThreadService.Status.ERROR;
                Log.e("IThreadTask Error", "Run Error", ex);
            }
        } finally {
            synchronized (this) {
                ThreadService.threadTaskByThread.set(null);
                if ((--count) > 0) {
                    if (handle.postDelayed(this, timeReplay)) {
                        status = ThreadService.Status.QUEUE;
                    } else {
                        status = ThreadService.Status.STOP;
                        unqueNumWork.remove(uniqueNum);
                    }
                } else if (status != ThreadService.Status.ERROR) {
                    status = ThreadService.Status.DONE;
                    unqueNumWork.remove(uniqueNum);
                }
            }
        }
    }

    synchronized void _start() {
        if (threadLooper == null || !threadLooper.isWorks()) {
            Log.e("IThreadTask error", "Start task in close Handel!!!", new IllegalAccessException());
            return;
        }
        if (count == 0) {
            Log.e("IThreadTask error", "Start task with count = 0", new IllegalAccessError());
            return;
        }
        if (timeStart < 0) timeStart = 0;
        if (timeReplay <= 0) timeReplay = 1;
        if (count == ThreadService.NONE) count = 1;
        uniqueNum = getUniqueNumGen(uniqueNum, this);
        isStop = false;
        switch (unique) {
            case OLD://Not release
            {
                ThreadTask old = unqueNumWork.get(uniqueNum);
                if(
                        old != null &&
                        old != this &&
                        old.status.isOK &&
                        old.status != ThreadService.Status.DONE
                ) break;
                if(old != null){
                    old.stop();
                }
            }
            case NEW: {//Not release
                unqueNumWork.remove(uniqueNum);
                handle.removeCallbacksAndMessages(uniqueNum);
            }
            case NONE: {
                Message m = Message.obtain(handle, this);
                m.obj = uniqueNum;
                if (handle.sendMessageDelayed(m, timeStart)) {
                    status = ThreadService.Status.QUEUE;
                    unqueNumWork.put(uniqueNum, this);
                } else {
                    status = ThreadService.Status.STOP;
                    unqueNumWork.remove(uniqueNum);
                }
            }
        }
    }

    public synchronized void start() {
        isStop = false;
        if (count > 0 && status == ThreadService.Status.STOP) {
            switch (unique) {
                case OLD: //Not release
                {
                    ThreadTask old = unqueNumWork.get(uniqueNum);
                    if(
                            old != null &&
                            old != this &&
                            old.status.isOK &&
                            old.status != ThreadService.Status.DONE
                    ) break;
                    if(old != null){
                        old.stop();
                    }
                }
                case NEW: {//Not release
                    unqueNumWork.remove(uniqueNum);
                    handle.removeCallbacksAndMessages(uniqueNum);
                }
                case NONE: {
                    Message m = Message.obtain(handle, this);
                    m.obj = uniqueNum;
                    if (handle.sendMessageDelayed(m, timeStart)) {
                        status = ThreadService.Status.QUEUE;
                        unqueNumWork.put(uniqueNum, this);
                    } else {
                        status = ThreadService.Status.STOP;
                        unqueNumWork.remove(uniqueNum);
                    }
                }
            }
        }
    }

    public synchronized void stop() {
        isStop = true;
        handle.removeCallbacks(this, uniqueNum);
        status = ThreadService.Status.STOP;
        unqueNumWork.remove(uniqueNum);
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
        if (status == ThreadService.Status.RUN && ThreadService.threadTaskByThread.get() != threadLooper) {
            status = ThreadService.Status.STOP;
            handle.removeCallbacks(this);
        }
        return status;
    }

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

    public void _init() {
    }

    public void _deinit() {
    }

    @NonNull
    @Override
    public ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncMain(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return threadLooper.asyncIO(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @NonNull
    @Override
    public ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
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

    public void onThreadProgressListener(ThreadProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    private static int uniqueNumGen = 0;
    private static Map<Integer, ThreadTask> unqueNumWork = Collections.synchronizedMap(new HashMap<>());


    public static int getUniqueNumGen(){
        return getUniqueNumGen(ThreadService.NONE, null);
    }

    private static synchronized int getUniqueNumGen(int uniqueNum, ThreadTask task){
        if(uniqueNum == ThreadService.NONE) {
            do {
                uniqueNum = uniqueNumGen++;
            } while (unqueNumWork.containsKey(uniqueNum));
        }
        if(!unqueNumWork.containsKey(uniqueNum)){
            unqueNumWork.put(uniqueNum, task);
        }
        return uniqueNum;
    }
}