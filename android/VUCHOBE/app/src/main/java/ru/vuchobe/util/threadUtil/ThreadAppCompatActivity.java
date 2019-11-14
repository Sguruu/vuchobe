package ru.vuchobe.util.threadUtil;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * реализация AppCompatActivity для возможности вызова асснхроного выполнения
 * PS добавлять такую же реализацию (копию) для всех необходимых UI элементов
 * по необходимости
 */
public abstract class ThreadAppCompatActivity extends AppCompatActivity implements ThreadLooperLocal {
    private ThreadLooperGlobalImpl looperGlobal = new ThreadLooperGlobalImpl(this);
    private ThreadLooperLocalImpl looperLocal = new ThreadLooperLocalImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _init();
        if (ThreadService.contextGlobal == null) {
            ThreadService.contextGlobal = this;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThreadService.contextGlobal = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _deinit();
    }

    @Override
    public void _init() {
        looperGlobal._init();
        looperLocal._init();
    }

    @Override
    public void _deinit() {
        looperGlobal._deinit();
        looperLocal._deinit();
    }

    @Override
    public @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.looperGlobal.asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.looperGlobal.asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.looperGlobal.asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.looperLocal.asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.looperLocal.asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return this.looperLocal.asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    @Override
    public boolean isWorks() {
        return this.looperLocal.isWorks();
    }

    @Override
    public @Nullable
    Context getContext() {
        return this;
    }
}