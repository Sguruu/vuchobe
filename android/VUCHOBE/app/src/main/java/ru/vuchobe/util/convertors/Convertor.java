package ru.vuchobe.util.convertors;

import java.io.InputStream;
import java.nio.charset.Charset;

public interface Convertor {
    <T> InputStream encode(String type, T obj, Class<T> clazz) throws Exception;

    <T> T decode(String type, InputStream stream, Class<T> clazz) throws Exception;

    Convertor getForCharset(String charset);

    Convertor getForCharset(Charset charset);
}
