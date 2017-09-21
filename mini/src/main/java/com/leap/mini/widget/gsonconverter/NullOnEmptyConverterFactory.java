package com.leap.mini.widget.gsonconverter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;

/**
 * 页面描述：
 * <p>
 * Created by ditclear on 16/10/20.
 */

public class NullOnEmptyConverterFactory extends Factory {


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            }
        };
    }
}