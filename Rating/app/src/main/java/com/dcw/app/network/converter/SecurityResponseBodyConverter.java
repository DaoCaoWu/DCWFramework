package com.dcw.app.network.converter;

import com.dcw.framework.data.net.client.Defaults;
import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import cn.uc.security.MessageDigest;
import okio.BufferedSource;
import okio.Okio;
import retrofit.Converter;

/**
 * create by adao12.vip@gmail.com on 15/12/5
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class SecurityResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {

    @Override
    public ResponseBody convert(ResponseBody value) throws IOException {
        byte[] inputBytes = new byte[4098];
        value.byteStream().read(inputBytes);
        MessageDigest.m9Decode(inputBytes, Defaults.SECRET_KEY);
        int[] offsetAndLength = new int[]{MessageDigest.M9_DECODE_DEST_OFFSET, inputBytes.length - MessageDigest.M9_DECODE_DEST_OFFSET_LENGTH};
        InputStream is = new ByteArrayInputStream(inputBytes, offsetAndLength[0], offsetAndLength[1]);
        GZIPInputStream in = new GZIPInputStream(is);
        BufferedSource source = Okio.buffer(Okio.source(in));
        return ResponseBody.create(value.contentType(), value.contentLength(), source);
    }
}
