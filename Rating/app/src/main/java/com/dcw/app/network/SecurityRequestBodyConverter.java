package com.dcw.app.network;

import com.dcw.framework.data.net.client.Defaults;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import cn.uc.security.MessageDigest;
import okio.BufferedSink;
import okio.Okio;
import retrofit.Converter;

/**
 * create by adao12.vip@gmail.com on 15/12/5
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class SecurityRequestBodyConverter implements Converter<RequestBody, RequestBody> {

    @Override
    public RequestBody convert(RequestBody value) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedSink sink = Okio.buffer(Okio.sink(new GZIPOutputStream(baos)));
        value.writeTo(sink);
        sink.flush();
        sink.close();
        byte[] outputBytes = baos.toByteArray();
        outputBytes = MessageDigest.m9Encode(Defaults.PLATFORM_GAME, outputBytes, Defaults.SECRET_KEY);
        baos.flush();
        baos.close();
        return RequestBody.create(value.contentType(), outputBytes);
    }
}
