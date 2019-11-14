package ru.vuchobe.util.convertors;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonConvertor /*extends InputStream*/ implements Convertor {

    private static Gson gson = new Gson();

    public JsonConvertor() {
    }

    //TODO можно изменить но не сегодня
    @Override
    public <T> InputStream encode(String type, T obj, Class<T> clazz) throws Exception {
        String string = gson.toJson(obj, clazz);
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    //TODO можно изменить но не сегодня
    @Override
    public <T> T decode(String type, InputStream stream, Class<T> clazz) throws Exception {
        return gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), clazz);
    }
}
