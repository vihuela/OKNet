package ricky.oknet.interceptor;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;


import java.io.IOException;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import ricky.oknet.utils.JsonPrinter;

public class LoggerInterceptor implements Interceptor {
    private String tag;
    private boolean showResponse;
    private Lock loggingLock = new ReentrantLock();
    private boolean isDebug = true;
    private static final String PREFIX = "  [";
    private String requestUrl;
    private long startTime;


    public LoggerInterceptor(boolean isDebug, boolean showResponse, String tag) {
        this.isDebug = isDebug;
        this.showResponse = showResponse;
        this.tag = tag;
        JsonPrinter.TAG = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (isDebug) {
            try {
                loggingLock.lock();
                Request request = chain.request();
                logForRequest(request);
                Response response = chain.proceed(request);
                return logForResponse(response);
            } finally {
                loggingLock.unlock();
            }
        } else {
            return chain.proceed(chain.request());
        }


    }

    private void logForRequest(Request request) {
        try {
            startTime = System.nanoTime();

            HttpUrl hurl = request.url();
            requestUrl = hurl.toString();
            Headers headers = request.headers();

            Log.e(tag, "---------------------Request Start---------------------");
            Log.e(tag, "---REQ : " + requestUrl + "\n");
            Log.e(tag, "Path：" + "\n");
            String pathMsg = "";
            if (!TextUtils.isEmpty(request.method())) {
                pathMsg += PREFIX + "RequestType：" + request.method() + "] " + "\n";
            }
            if (!TextUtils.isEmpty(hurl.host())) {
                pathMsg += PREFIX + "Host：" + request.url().host() + "] " + "\n";
            }
            if (hurl.port() >= 1 && hurl.port() <= 65535)
                pathMsg += PREFIX + "Port：" + request.url().port() + "] " + "\n";
            Log.e(tag, pathMsg);
            //Header
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "Headers：" + "\n");
                for (String item : headers.names()) {
                    if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(headers.get(item))) {
                        Log.e(tag, PREFIX + item + "：" + headers.get(item) + "]" + "\n");
                    }
                }
            }
            //for get
            Set<String> namePairs = hurl.queryParameterNames();
            if (namePairs != null && namePairs.size() > 0) {
                Log.e(tag, "Params：" + "\n");
                for (int i = 0; i < namePairs.size(); i++) {
                    String key = hurl.queryParameterName(i);
                    String val = hurl.queryParameterValue(i);
                    if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(val)) {
                        Log.e(tag, PREFIX + key + "：" + val + "]" + "\n");
                    }
                }
            }
            //for post
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                Log.e(tag, "Params：" + "\n");
                //Param none

                //contentType
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    String str = mediaType.toString();
                    Log.e(tag, PREFIX + "ContentType：" + str + "]");
                    if (isText(mediaType)) {
                        Log.e(tag, PREFIX + "Content：" + bodyToString(request) + "]");
                    } else {
                        String defaultMsg = PREFIX + "Content：" + " maybe [file part] , too large too print , ignored!" + "]";
                        Log.e(tag, defaultMsg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response logForResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            final HttpUrl url = clone.request().url();

            long stopTime = System.nanoTime();


            @SuppressLint("DefaultLocale")
            String RES = String.format("---RES : [%.1fms] %s", (stopTime - startTime) / 1e6d, (url.toString().equals(requestUrl) ? "" : url));
            //Redirect see url
            Log.e(tag, RES);

            String resMsg = "[Protocol：" + clone.protocol() + "]" +
                    "  [Code：" + clone.code() + "]" +
                    (!TextUtils.isEmpty(clone.message()) ? "  [Message：" + clone.message() + "]" : "");

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {

                        Log.e(tag, resMsg + "  [ContentType：" + mediaType.toString() + "]");
                        if (isText(mediaType)) {
                            String resp = body.string();
                            //json
                            if (resp.startsWith("{") || resp.startsWith("[")) {
                                JsonPrinter.json(resp);
                            } else {
                                Log.e(tag, "Content : " + resp);
                            }
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.e(tag, "Content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    } else {
                        Log.e(tag, resMsg);
                    }
                } else {
                    Log.e(tag, resMsg);
                }
            } else {
                Log.e(tag, resMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.e(tag, "---------------------Request End---------------------");
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) //
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
