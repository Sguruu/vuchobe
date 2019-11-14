package ru.vuchobe.util.threadUtil;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ThreadTask extends ThreadLooper {
    void work();

    default void start() {
    }

    default void stop() {
    }

    @Nullable
    default ThreadLooper getThreadLooper() {
        return ThreadService.get().getThreadLooper();
    }

    default ThreadService.Unique getUnique() {
        return ThreadService.get().getUnique();
    }

    default int getUniqueNum() {
        return ThreadService.get().getUniqueNum();
    }

    default int getTimeStart() {
        return ThreadService.get().getTimeStart();
    }

    default int getTimeReplay() {
        return ThreadService.get().getTimeReplay();
    }

    default int getCount() {
        return ThreadService.get().getCount();
    }

    default ThreadService.Status getStatus() {
        return ThreadService.get().getStatus();
    }

    default void addProgress(long add) {
        ThreadService.get().addProgress(add);
    }

    default long getProgress() {
        return ThreadService.get().getProgress();
    }

    default long getProgressMax() {
        return ThreadService.get().getProgressMax();
    }

    default void setProgress(long progress) {
        ThreadService.get().setProgress(progress);
    }

    default void setProgressMax(long progressMax) {
        ThreadService.get().setProgressMax(progressMax);
    }

    default void onThreadProgressListener(ThreadProgressListener progressListener) {
    }

    default void _init() {
    }

    default void _deinit() {
    }

    default @NonNull
    ThreadTask asyncMainGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIOGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetworkGlobal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncMainLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIOLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetworkLocal(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncMain(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncIO(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return ThreadService.get().asyncNetwork(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default boolean isWorks() {
        return ThreadService.get().isWorks();
    }

    default @Nullable
    Context getContext() {
        return ThreadService.get().getContext();
    }
}