package ru.vuchobe.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import ru.vuchobe.util.convertors.Convertor;
import ru.vuchobe.util.convertors.JsonConvertor;
import ru.vuchobe.util.threadUtil.ThreadService;
import ru.vuchobe.util.threadUtil.ThreadTask;

public class NetworkManager {
    private static final String name = "Сервиз Передачи данных через inet";
    public static String token = "";
    public static String refreshToken = "";
    public static String host = "";

    public enum Method {
        GET("GET"),
        POST("POST"),
        DELETE("DELETE"),
        CREATE("CREATE"),
        HEAD("HEAD"),
        OPTIONS("OPTIONS"),
        PUT("PUT"),
        PATCH("PATCH"),
        TRACE("TRACE"),
        CONNECT("CONNECT");

        private String method;

        Method(String method) {
            this.method = method;
        }

        public String get() {
            return method;
        }
    }

    public static <K, T> void objectObject(String path, Method method, String type, Class<K> typeClass, K body, Class<T> typeClass2, ManagerService.AsyncResult<T, NetworkException> result) {
        InputStream is = null;
        int size = 0;
        try {
            is = getTypeConvertor(type).encode(type, body, typeClass);
            if (is instanceof ByteArrayInputStream) {
                size = ((ByteArrayInputStream) is).available();
            }
        } catch (Exception ex) {
            //TODO edit-error
            return;
        }
        try (InputStream inputStream = new ProgressInputStream(is, size)) {
            inputObject(path, method, type, inputStream, size, typeClass2, result);
            return;
        } catch (IOException ex) {
            //TODO edit-error
            return;
        } catch (Exception ex) {
            //TODO edit-error
            return;
        }
    }

    public static <K> void objectOutput(String path, Method method, String type, Class<K> typeClass, K body, ManagerService.AsyncResult<HttpURLConnection, NetworkException> result) {
        InputStream is;
        int size = 0;
        try {
            is = getTypeConvertor(type).encode(type, body, typeClass);
            if (is instanceof ByteArrayInputStream) {
                size = ((ByteArrayInputStream) is).available();
            }
        } catch (Exception ex) {
            //TODO edit-error
            return;
        }

        try (InputStream inputStream = new ProgressInputStream(is, size)) {
            inputOutput(path, method, type, inputStream, size, result);
            return;
        } catch (IOException ex) {
            //TODO edit-error
            return;
        } catch (Exception ex) {
            //TODO edit-error
            return;
        }
    }

    public static <T> void inputObject(String path, Method method, String type, InputStream input, int size, Class<T> typeClass, ManagerService.AsyncResult<T, NetworkException> result) {
        inputOutput(path, method, type, input, size, (body, error) -> {
            if (body == null || error != null) {
                result.result(null, error);
                return;
            }
            try (BufferedInputStream inputStream = new ProgressInputStream(body.getInputStream(), body.getContentLength())) {
                String contentType = body.getContentType();
                String encoding = body.getContentEncoding();

                T obj = getTypeConvertor(contentType, encoding).decode(contentType, inputStream, typeClass);
                result.result(obj, null);
                return;
            } catch (IOException ex) {
                //TODO edit - error
            } catch (Exception ex) {
                //TODO edit - error
            }
        });
    }

    public static void inputOutput(String path, Method method, String type, InputStream input, int size, ManagerService.AsyncResult<HttpURLConnection, NetworkException> result) {
        HttpURLConnection connection = null;
        try {
            ThreadTask threadTask = ThreadService.get();
            if (threadTask != null) {
                threadTask.setProgressMax((size > 0) ? size : 100);
            }

            URL url = new URL(host + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.get());
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if (type == null) {
                type = "application/json";
            }
            if (input != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", type);
                if (size > 0) {
                    connection.setFixedLengthStreamingMode(size);
                } else {
                    connection.setChunkedStreamingMode(0);
                }
                try (OutputStream ostream = connection.getOutputStream(); BufferedInputStream istream = new ProgressInputStream(input, size)) {
                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = istream.read(buff)) != -1) {
                        //write and block
                        ostream.write(buff, 0, len);
                    }
                } catch (IOException ex) {
                    //TODO edit - error
                }
            }
            //получаем ответ
            result.result(connection, null);
        } catch (MalformedURLException ex) {
            //TODO edit - error
        } catch (IOException ex) {
            //TODO edit - error
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private static JsonConvertor jsonConvertor = new JsonConvertor();

    public static Convertor getTypeConvertor(String type) {
        return getTypeConvertor(type, "UTF-8");//TODO см у типа а лишь потом брать utf-8
    }

    public static Convertor getTypeConvertor(String type, String encoding) {
        return jsonConvertor;
    }

    public static class NetworkException extends ManagerService.ServiceException {
        public NetworkException() {
            super();
        }

        public NetworkException(@NonNull Throwable cause) {
            super(cause);
        }

        @NonNull
        @Override
        public String getServiceName() {
            return NetworkManager.name;
        }
    }

    public static class ProgressInputStream extends BufferedInputStream {
        ThreadTask task;
        long sizeData;
        long progressStart = 0;
        long position = 0;
        boolean isUpdate;

        public ProgressInputStream(InputStream input, long size) {
            super(input);
            task = ThreadService.get();
            sizeData = size;
            if (task != null) {
                progressStart = task.getProgress();
                task.setProgressMax(task.getProgressMax() + ((sizeData > 0) ? sizeData : 100L));
            }
            isUpdate = task != null && sizeData > 0;
        }

        @Override
        public synchronized int read() throws IOException {
            int result = super.read();
            if (result > 0 && isUpdate) {
                position++;
                task.setProgress(task.getProgress() + position);
            }
            return result;
        }

        @Override
        public int read(byte[] b) throws IOException {
            return read(b, 0, b.length);
        }

        @Override
        public synchronized int read(byte[] b, int off, int len) throws IOException {
            int length = super.read(b, off, len);
            if (length > 0 && isUpdate) {
                position += length;
                task.setProgress(task.getProgress() + position);
            }
            return length;
        }

        @Override
        public synchronized long skip(long n) throws IOException {
            long length = super.skip(n);
            if (length > 0 && isUpdate) {
                position += length;
                task.setProgress(progressStart + position);
            }
            return length;
        }

        @Override
        public synchronized void reset() throws IOException {
            if (markpos > 0 && isUpdate) {
                task.setProgress(task.getProgress() + markpos - pos);
            }
            super.reset();
        }

        @Override
        public void close() throws IOException {
            if (task != null) {
                task.setProgressMax(progressStart + position);
                task.setProgress(task.getProgressMax());
            }
            super.close();
        }
    }
}
