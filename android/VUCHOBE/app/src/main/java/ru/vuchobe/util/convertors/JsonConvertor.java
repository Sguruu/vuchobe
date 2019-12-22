package ru.vuchobe.util.convertors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class JsonConvertor /*extends InputStream*/ implements Convertor {

    private static Gson gson = new Gson();
    private Charset charset = Charset.forName("utf-8");

    public JsonConvertor() {

    }

    private JsonConvertor(Charset charset) {
        this.charset = charset;
    }

    //TODO можно изменить но не сегодня
    @Override
    public <T> InputStream encode(String type, T obj, Class<? extends T> clazz) throws Exception {
        String string = gson.toJson(obj, clazz);
        return new ByteArrayInputStream(string.getBytes(charset));
    }

    //TODO можно изменить но не сегодня
    @Override
    public <T> T decode(String type, InputStream stream, Class<? extends T> clazz) throws Exception {
        return gson.fromJson(new InputStreamReader(stream, charset), clazz);
    }

    private static HashMap<String, JsonConvertor> cache = new HashMap<>();

    @Override
    public JsonConvertor getForCharset(Charset charset) {
        if (charset == null) return this;
        String charsetName = charset.name().toLowerCase();
        if (charsetName.isEmpty() || charsetName.equals("utf-8")) return this;

        JsonConvertor result = cache.get(charsetName);
        if (result == null) {
            result = new JsonConvertor(charset);
            cache.put(charsetName, result);
        }
        return result;
    }

    @Override
    public JsonConvertor getForCharset(String charset) {
        if (charset == null || charset.isEmpty() || charset.equals("utf-8")) return this;
        return getForCharset(Charset.forName(charset));
    }
}
