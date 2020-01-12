package ru.vuchobe.util.threadUtil;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.Nullable;

public class ThreadService {
    public static final int NONE = -1;

    static final Handler mainGlobal;

    static final HandlerThread handelThreadIOGlobal;
    static final Looper looperIOGlobal;
    static final Handler ioGlobal;

    static final HandlerThread handelThreadNetworkGlobal;
    static final Looper looperNetworkGlobal;
    static final Handler networkGlobal;

    static final ThreadLooperGlobal asyncGlobal;

    static final ThreadLocal<ThreadTask> threadTaskByThread = new ThreadLocal<>();

    static Context contextGlobal = null;

    static {
        mainGlobal = new Handler(Looper.getMainLooper());

        handelThreadIOGlobal = new HandlerThread("Global IO");
        handelThreadIOGlobal.start();
        handelThreadNetworkGlobal = new HandlerThread("Global Network");
        handelThreadNetworkGlobal.start();

        looperIOGlobal = handelThreadIOGlobal.getLooper();
        looperNetworkGlobal = handelThreadNetworkGlobal.getLooper();

        ioGlobal = new Handler(looperIOGlobal);
        networkGlobal = new Handler(looperNetworkGlobal);

        asyncGlobal = new ThreadLooperGlobalImpl();
    }

    private static volatile int uniqueNumber = 0;

    public static synchronized int getUnique() {
        return uniqueNumber++;
    }

    public enum Unique {NONE, OLD, NEW}

    public enum Status {
        NONE(false), QUEUE(true), RUN(true), DONE(true), ERROR(false), STOP(false);

        public final boolean isOK;

        Status(boolean isOK) {
            this.isOK = isOK;
        }
    }

    public static @Nullable
    ThreadTask get() {
        ThreadTask result = threadTaskByThread.get();
        if (result == null) {
            throw new NullPointerException(" ThreadLooper == null");
        }
        return result;
    }
}
