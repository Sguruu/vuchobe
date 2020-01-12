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
import ru.vuchobe.util.NetworkUtils.ProgressInputStream;
import ru.vuchobe.util.convertors.Convertor;
import ru.vuchobe.util.convertors.JsonConvertor;
import ru.vuchobe.util.threadUtil.ThreadService;
import ru.vuchobe.util.threadUtil.ThreadTask;

public class NetworkManager {
    private static final String name = "Сервиз Передачи данных через inet";
    public static String token = "";
    public static String refreshToken = "";
    public static String host = "http://vuchobe.com/api/v1";

    public static String mimeJson = "application/json; charset=utf-8";

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

    private static final ByteArrayInputStream EMPTY_BYTE_INPUT_STREAM = new ByteArrayInputStream(new byte[0]);
    public static <K, T> void objectObject(String path, Method method, String type, Class<? extends K> typeClass, K body, Class<? extends T> typeClass2, ManagerService.AsyncResult<T, NetworkException> result) {
        InputStream is = null;
        int size = 0;
        try {
            if(type == null && body == null && typeClass == null){
                is = EMPTY_BYTE_INPUT_STREAM;
            }else {
                is = getTypeConvertor(type).encode(type, body, typeClass);
                if (is instanceof ByteArrayInputStream) {
                    size = ((ByteArrayInputStream) is).available();
                }
            }
        } catch (Exception ex) {
            //TODO edit-error

            result.run(null, new NetworkException(ex));
            return;
        }
        try (InputStream inputStream = new ProgressInputStream(is, size)) {
            inputObject(path, method, type, inputStream, size, typeClass2, result);
        } catch (IOException ex) {
            //TODO edit-error
            result.run(null, new NetworkException(ex));
        } catch (Exception ex) {
            //TODO edit-error
            result.run(null, new NetworkException(ex));
        }
    }

    public static <K> void objectOutput(String path, Method method, String type, Class<K> typeClass, K body, ManagerService.AsyncResult<HttpURLConnection, NetworkException> result) {
        InputStream is;
        int size = 0;
        try {
            if(type == null && body == null && typeClass == null){
                is = EMPTY_BYTE_INPUT_STREAM;
            }else {
                is = getTypeConvertor(type).encode(type, body, typeClass);
                if (is instanceof ByteArrayInputStream) {
                    size = ((ByteArrayInputStream) is).available();
                }
            }
        } catch (Exception ex) {
            //TODO edit-error
            result.run(null, new NetworkException(ex));
            return;
        }

        try (InputStream inputStream = new ProgressInputStream(is, size)) {
            inputOutput(path, method, type, inputStream, size, result);
        } catch (IOException ex) {
            //TODO edit-error
            result.run(null, new NetworkException(ex));
        } catch (Exception ex) {
            //TODO edit-error
            result.run(null, new NetworkException(ex));
        }
    }

    public static <T> void inputObject(String path, Method method, String type, InputStream input, int size, Class<? extends T> typeClass, ManagerService.AsyncResult<T, NetworkException> result) {
        inputOutput(path, method, type, input, size, (body, error) -> {
            if (body == null || error != null) {
                result.run(null, error);
                return;
            }
            try (BufferedInputStream inputStream = new ProgressInputStream(body.getInputStream(), body.getContentLength())) {
                String contentType = body.getContentType();
                String encoding = body.getContentEncoding();

                T obj = getTypeConvertor(contentType, encoding).decode(contentType, inputStream, typeClass);
                result.run(obj, null);
                return;
            } catch (IOException ex) {
                //TODO edit - error
                result.run(null, new NetworkException(ex));
            } catch (Exception ex) {
                //TODO edit - error
                result.run(null, new NetworkException(ex));
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
            if (token != null && !token.isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(6000);

            if (input != null && (method != Method.GET || (type != null && type.isEmpty()))) {
                if (type == null) {
                    type = "application/json";
                }
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
                    result.run(connection, new NetworkException(ex));
                    return;
                }
            }
            //получаем ответ
            connection.connect();
            //TODO можно узнать код ошибки 404 и чтение из другого потока connection.getErrorStream()
            result.run(connection, null);
        } catch (MalformedURLException ex) {
            //TODO edit - error
            result.run(connection, new NetworkException(ex));
        } catch (IOException ex) {
            //TODO edit - error
            result.run(connection, new NetworkException(ex));
        } finally {
            //if (connection != null) connection.disconnect();
        }
    }

    private static JsonConvertor jsonConvertor = new JsonConvertor();

    public static Convertor getTypeConvertor(String type) {
        return getTypeConvertor(type, "");
    }

    public static Convertor getTypeConvertor(String type, String encoding) {
        String[] list = type.split(";");
        String typeReal = list[0].trim().toLowerCase();
        if (list.length > 1) {
            for (String elem : list) {
                elem = elem.trim().toLowerCase();
                String[] list2 = elem.split("=");
                if (list2.length >= 2 && list2[0].startsWith("charset")) {
                    encoding = list2[1].trim();
                }
            }
        }
        switch (typeReal) {
            case "application/json":
            case "text/json": {
                return jsonConvertor.getForCharset(encoding);
            }
        }
        return jsonConvertor;//default convertor
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
}
