package ru.vuchobe.util.threadUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Реализация глобального ассинхроного выполнения
 */
class ThreadLooperGlobalImpl implements ThreadLooperGlobal {

    private @Nullable
    ThreadLooperLocal local;

    public ThreadLooperGlobalImpl() {
        this(null);
    }

    public ThreadLooperGlobalImpl(@Nullable ThreadLooperLocal local) {
        this.local = local;
    }

    @Override
    public void _init() {
    }

    @Override
    public void _deinit() {
        local = null;
    }

    @Override
    public @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.ioGlobal, this, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.mainGlobal, this, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.networkGlobal, this, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        ThreadLooperLocal local = this.local;
        if (local != null) {
            return local.asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
        }
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.mainGlobal, null, task);//error threadLooperByThread == null
    }

    @Override
    public @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        ThreadLooperLocal local = this.local;
        if (local != null) {
            return local.asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
        }
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.ioGlobal, null, task);//error threadLooperByThread == null
    }

    @Override
    public @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        ThreadLooperLocal local = this.local;
        if (local != null) {
            return local.asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
        }
        return this.asyncRun(unique, uniqueNum, timeStart, timeReplay, count, ThreadService.networkGlobal, null, task);//error threadLooperByThread == null
    }
}