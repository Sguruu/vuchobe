package ru.vuchobe.util.threadUtil;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Интерфейс для реализации глобального ассинхроного выполнения 2-уровня
 */
public interface ThreadLooperGlobal extends ThreadLooper {
    default @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return asyncMainGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return asyncIOGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull ThreadTask task) {
        return asyncNetworkGlobal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @Nullable
    Context getContext() {
        return ThreadService.contextGlobal;
    }

    default boolean isWorks() {
        return true;
    }
}