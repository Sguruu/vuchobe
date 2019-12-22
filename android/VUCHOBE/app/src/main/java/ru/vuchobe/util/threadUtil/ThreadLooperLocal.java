package ru.vuchobe.util.threadUtil;

import androidx.annotation.NonNull;

/**
 * Интерфейс для реализации локалного ассинхроного выполнения 2-уровня
 */
public interface ThreadLooperLocal extends ThreadLooper {
    default @NonNull
    ThreadTask asyncMain(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return asyncMainLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncIO(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return asyncIOLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }

    default @NonNull
    ThreadTask asyncNetwork(ThreadService.Unique unique, int uniqueNum, int timeStart, int timeReplay, int count, @NonNull IThreadTask task) {
        return asyncNetworkLocal(unique, uniqueNum, timeStart, timeReplay, count, task);
    }
}