package com.dcw.app.network.converter;

import com.dcw.app.presentation.test.model.I;
import com.dcw.app.presentation.test.model.State;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.UnknownServiceException;

import retrofit.Converter;

/**
 * create by adao12.vip@gmail.com on 15/12/3
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody originValue) throws IOException {
        ResponseBody value = new SecurityResponseBodyConverter().convert(originValue);
        Reader reader = value.charStream();
        try {
            I in = gson.fromJson(reader, I.class);
            State state = in.getState();
            if (state != null && state.getCode() == 2000000) {
                return gson.fromJson(gson.toJson(in.getData()), type);
            } else if (state != null) {
                throw new UnknownServiceException(state.getMsg());
            }
        } catch (Exception e) {
            throw new UnknownServiceException(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ignored) {
            }
        }
        return null;
    }
}
