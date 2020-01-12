package ru.vuchobe.util.threadUtil;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Интерфейс для реализации ассинхроного выполнения 1-уровня обобщеный
 */
public interface ThreadLooper {
    void _init();

    void _deinit();

    @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task);

    @Nullable
    Context getContext();

    boolean isWorks();


    default @NonNull
    ThreadTask asyncIOGlobal(@NonNull IThreadTask task) {
        return this.asyncIOGlobal(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(int timeStart, @NonNull IThreadTask task) {
        return this.asyncIOGlobal(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncIOGlobal(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncIOGlobal(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncIOGlobal(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncIOGlobal(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(@NonNull IThreadTask task) {
        return this.asyncMainGlobal(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(int timeStart, @NonNull IThreadTask task) {
        return this.asyncMainGlobal(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncMainGlobal(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncMainGlobal(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncMainGlobal(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncMainGlobal(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(@NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(int timeStart, @NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }


    default @NonNull
    ThreadTask asyncMainLocal(@NonNull IThreadTask task) {
        return this.asyncMainLocal(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(int timeStart, @NonNull IThreadTask task) {
        return this.asyncMainLocal(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncMainLocal(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncMainLocal(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncMainLocal(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncMainLocal(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(@NonNull IThreadTask task) {
        return this.asyncIOLocal(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(int timeStart, @NonNull IThreadTask task) {
        return this.asyncIOLocal(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncIOLocal(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncIOLocal(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncIOLocal(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncIOLocal(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(@NonNull IThreadTask task) {
        return this.asyncNetworkLocal(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(int timeStart, @NonNull IThreadTask task) {
        return this.asyncNetworkLocal(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncNetworkLocal(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncNetworkLocal(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncNetworkLocal(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncNetworkLocal(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }


    default @NonNull
    ThreadTask asyncMain(@NonNull IThreadTask task) {
        return this.asyncMain(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMain(int timeStart, @NonNull IThreadTask task) {
        return this.asyncMain(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMain(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncMain(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMain(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncMain(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncMain(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncMain(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncMain(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIO(@NonNull IThreadTask task) {
        return this.asyncIO(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIO(int timeStart, @NonNull IThreadTask task) {
        return this.asyncIO(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIO(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncIO(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIO(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncIO(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncIO(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncIO(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncIO(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(@NonNull IThreadTask task) {
        return this.asyncNetwork(ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(int timeStart, @NonNull IThreadTask task) {
        return this.asyncNetwork(timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncNetwork(timeStart, timeReplay, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncNetwork(ThreadService.Unique.NONE, ThreadService.NONE, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, @NonNull IThreadTask task) {
        return this.asyncNetwork(unique, uniqueNum, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, @NonNull IThreadTask task) {
        return this.asyncNetwork(unique, uniqueNum, timeStart, ThreadService.NONE, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, @NonNull IThreadTask task) {
        return this.asyncNetwork(unique, uniqueNum, timeStart, timeReplay, ThreadService.NONE, task);
    }

    default ThreadTask asyncRun(
            ThreadService.Unique unique,
            int uniqueNum,
            int timeStart,
            int timeReplay,
            int count,
            @NonNull Handler handle,
            @Nullable ThreadLooper threadLooper,
            @NonNull IThreadTask task
    ) {
        ThreadTask taskReal = new ThreadTask();
        taskReal.task = task;
        /*if(task.status != ThreadService.Status.NONE){
            throw new IllegalArgumentException("task.status != Status.NONE");
        }*/
        taskReal.handle = handle;
        taskReal.threadLooper = threadLooper;
        taskReal.unique = unique;
        taskReal.uniqueNum = uniqueNum;
        taskReal.timeStart = timeStart;
        taskReal.timeReplay = timeReplay;
        taskReal.count = count;
        taskReal._start();
        return taskReal;
    }
}
