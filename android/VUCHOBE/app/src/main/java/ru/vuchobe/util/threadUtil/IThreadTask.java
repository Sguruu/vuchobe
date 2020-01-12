package ru.vuchobe.util.threadUtil;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IThreadTask {
    void work(ThreadTask threadTask);
}