package ru.vuchobe.util.threadUtil;

import android.content.Context;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Реализация локалного ассинхроного выполнения
 */
class ThreadLooperLocalImpl implements ThreadLooperLocal {
    private @Nullable
    WeakReference<ThreadLooperLocal> local;

    public ThreadLooperLocalImpl(@NonNull ThreadLooperLocal local) {
        this.local = new WeakReference<>(local);
    }

    @Override
    public void _init() {
    }

    @Override
    public void _deinit() {
        local = new WeakReference<>(null);
    }

    @Override
    public @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        ThreadLooperLocal local = this.local.get();
        if (local != null) {
            return local.asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
        }
        return ThreadService.asyncGlobal.asyncIO(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        ThreadLooperLocal local = this.local.get();
        if (local != null) {
            return local.asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
        }
        return ThreadService.asyncGlobal.asyncMain(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        ThreadLooperLocal local = this.local.get();
        if (local != null) {
            return local.asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
        }
        return ThreadService.asyncGlobal.asyncNetwork(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.mainGlobal, this, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.ioGlobal, this, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.networkGlobal, this, task);
    }

    @Override
    public boolean isWorks() {
        return local != null;
    }

    @Override
    public @Nullable
    Context getContext() {
        ThreadLooperLocal local = this.local.get();
        return (local != null) ? local.getContext() : null;
    }
}