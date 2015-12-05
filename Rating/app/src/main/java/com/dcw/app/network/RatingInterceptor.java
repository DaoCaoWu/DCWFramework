package com.dcw.app.network;

import com.dcw.framework.data.net.client.Defaults;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import cn.uc.security.MessageDigest;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * create by adao12.vip@gmail.com on 15/11/23
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class RatingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedSink sink = Okio.buffer(Okio.sink(new GZIPOutputStream(baos)));
        body.writeTo(sink);
        sink.flush();
        sink.close();
        byte[] outputBytes = baos.toByteArray();
        outputBytes = MessageDigest.m9Encode(Defaults.PLATFORM_GAME, outputBytes, Defaults.SECRET_KEY);
        baos.flush();
        baos.close();
        body = RequestBody.create(body.contentType(), outputBytes);

        Response response = chain.proceed(request.newBuilder().post(body).build());

        ResponseBody responseBody = response.body();
        byte[] inputBytes = new byte[4098];
        responseBody.byteStream().read(inputBytes);
        MessageDigest.m9Decode(inputBytes, Defaults.SECRET_KEY);
        int[] offsetAndLength = new int[]{MessageDigest.M9_DECODE_DEST_OFFSET, inputBytes.length - MessageDigest.M9_DECODE_DEST_OFFSET_LENGTH};
        InputStream is = new ByteArrayInputStream(inputBytes, offsetAndLength[0], offsetAndLength[1]);
        GZIPInputStream in = new GZIPInputStream(is);
        BufferedSource source = Okio.buffer(Okio.source(in));
        responseBody = ResponseBody.create(responseBody.contentType(), responseBody.contentLength(), source);
        if (response.code() == HttpStatus.SC_OK) {
        }
        return response.newBuilder().body(responseBody).build();
    }
}
