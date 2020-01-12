package ru.vuchobe.service;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class ManagerService {

    /*Если необходимо будет делать сложную систему */
    //private static HashMap<Class, Object> services = new HashMap<>();

    /**
     * Реализация для обратного вызова в ассинхронных методах или задачах.
     *
     * @param <T> результат выполнения или обратный ответ ассинхронной задачи
     * @param <K> ошибка возникшая при выполнении ассинхронной задачи
     */
    public interface AsyncResult<T, K extends Throwable> {
        /**
         * Метод для получения обратного ответа от ассинхронной задачи
         *
         * @param result результат выполнения или обратный ответ ассинхронной задачи
         * @param error  ошибка возникшая при выполнении ассинхронной задачи
         */
        void result(@Nullable T result, @Nullable K error);

        default void run(@Nullable T result, @Nullable K error) {
            try {
                result(result, error);
            } catch (Throwable ex) {
                Log.e("AsyncResult", "CALLBACK ERROR", ex);
            }
        }
    }

    /**
     * Базовый класс ошибки для сервисов
     */
    public static abstract class ServiceException extends Exception {

        public ServiceException() {
            this(null);
        }

        public ServiceException(@Nullable Throwable cause) {
            super(cause);
        }

        /**
         * Метод для получения сервиса в котором произошла ошибка
         *
         * @return
         */
        public abstract @NonNull
        String getServiceName();
    }
}
