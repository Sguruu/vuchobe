package ru.vuchobe.util.threadUtil;

public interface ThreadProgressListener {
    void onUpdateProgress(long progress, long maxProgress);
}
