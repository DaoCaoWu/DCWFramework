//package com.dcw.app.rating.net.client;
//
//import com.squareup.okhttp.Headers;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.ResponseBody;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//
//import cn.uc.security.MessageDigest;
//import okio.BufferedSink;
//import retrofit.client.Client;
//import retrofit.client.Header;
//import retrofit.client.Request;
//import retrofit.client.Response;
//import retrofit.mime.TypedInput;
//import retrofit.mime.TypedOutput;
//
///**
// * <p>Title: ucweb</p>
// * <p/>
// * <p>Description: </p>
// * ......
// * <p>Copyright: Copyright (c) 2015</p>
// * <p/>
// * <p>Company: ucweb.com</p>
// *
// * @author JiaYing.Cheng
// * @version 1.0
// * @email adao12.vip@gmail.com
// * @create 15/10/20
// */
//public class RatingClient implements Client {
//    private final OkHttpClient client;
//
//    public RatingClient() {
//        this(generateDefaultOkHttp());
//    }
//
//    public RatingClient(OkHttpClient client) {
//        if (client == null) throw new NullPointerException("client == null");
//        this.client = client;
//    }
//
//    private static OkHttpClient generateDefaultOkHttp() {
//        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(Defaults.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
//        client.setReadTimeout(Defaults.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
//        return client;
//    }
//
//    static com.squareup.okhttp.Request createRequest(Request request) {
//        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder()
//                .url(request.getUrl())
//                .method(request.getMethod(), createRequestBody(request.getBody()));
//
//        List<Header> headers = request.getHeaders();
//        for (int i = 0, size = headers.size(); i < size; i++) {
//            Header header = headers.get(i);
//            String value = header.getValue();
//            if (value == null) value = "";
//            builder.addHeader(header.getName(), value);
//        }
//
//        return builder.build();
//    }
//
//    static Response parseResponse(com.squareup.okhttp.Response response) {
//        return new Response(response.request().urlString(), response.code(), response.message(),
//                createHeaders(response.headers()), createResponseBody(response.body()));
//    }
//
//    private static RequestBody createRequestBody(final TypedOutput body) {
//        if (body == null) {
//            return null;
//        }
//        final MediaType mediaType = MediaType.parse(body.mimeType());
//        return new RequestBody() {
//            @Override
//            public MediaType contentType() {
//                return mediaType;
//            }
//
//            @Override
//            public void writeTo(BufferedSink sink) throws IOException {
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                OutputStream os = new GZIPOutputStream(byteArrayOutputStream);
//                body.writeTo(os);
//                byte[] outputBytes = byteArrayOutputStream.toByteArray();
//                sink.outputStream().write(outputBytes);
//                sink.outputStream().write(MessageDigest.m9Encode(Defaults.PLATFORM_GAME, outputBytes, Defaults.M9_SECRET_KEY));
//                sink.outputStream().flush();
//                sink.outputStream().close();
//            }
//
//            @Override
//            public long contentLength() {
//                return body.length();
//            }
//        };
//    }
//
//    private static TypedInput createResponseBody(final ResponseBody body) {
//        try {
//            if (body.contentLength() == 0) {
//                return null;
//            }
//        } catch (IOException e) {
//            return null;
//        }
//        return new TypedInput() {
//            @Override
//            public String mimeType() {
//                MediaType mediaType = body.contentType();
//                return mediaType == null ? null : mediaType.toString();
//            }
//
//            @Override
//            public long length() {
//                try {
//                    return body.contentLength();
//                } catch (IOException e) {
//                    return 0;
//                }
//            }
//
//            @Override
//            public InputStream in() throws IOException {
//                byte[] outputBytes = new byte[4098];
//                body.byteStream().read(outputBytes);
//                body.byteStream().close();
//                MessageDigest.m9Decode(outputBytes, Defaults.M9_SECRET_KEY);
//                int[] offsetAndLength = new int[]{MessageDigest.M9_DECODE_DEST_OFFSET, outputBytes.length - MessageDigest.M9_DECODE_DEST_OFFSET_LENGTH};
//                InputStream is = new ByteArrayInputStream(outputBytes, offsetAndLength[0], offsetAndLength[1]);
//                return new GZIPInputStream(is);
//            }
//        };
//    }
//
//    private static List<Header> createHeaders(Headers headers) {
//        int size = headers.size();
//        List<Header> headerList = new ArrayList<Header>(size);
//        for (int i = 0; i < size; i++) {
//            headerList.add(new Header(headers.name(i), headers.value(i)));
//        }
//        return headerList;
//    }
//
//    @Override
//    public Response execute(Request request) throws IOException {
//        return parseResponse(client.newCall(createRequest(request)).execute());
//    }
//}
