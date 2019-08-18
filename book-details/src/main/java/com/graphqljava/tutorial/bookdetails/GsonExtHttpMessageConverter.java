package com.graphqljava.tutorial.bookdetails;

import com.google.gson.Gson;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.Reader;
import java.lang.reflect.Type;

public class GsonExtHttpMessageConverter extends GsonHttpMessageConverter
//        implements GenericHttpMessageConverter<Object>
{
//    private final GsonHttpMessageConverter converter;
//    private final StringHttpMessageConverter stringHttpMessageConverter;

    public GsonExtHttpMessageConverter() {

    }

    public GsonExtHttpMessageConverter(Gson gson) {
        super(gson);
    }

    @Override
    protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
//        if(resolvedType.equals(String.class)){
            char[] arr = new char[8 * 1024];
            StringBuilder buffer = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
                buffer.append(arr, 0, numCharsRead);
            }
            return buffer.toString();
//        }
//        return super.readInternal(resolvedType, reader);
    }
}
