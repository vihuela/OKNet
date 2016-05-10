package ricky.oknet.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import ricky.oknet.utils.JsonPrinter;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);

        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.e(tag, "---------------------Request Start---------------------");
            Log.e(tag, "---URL : " + url + request.method());
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "Headers : \n");
                Log.e(tag, headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response logForResponse(Response response) {
        try {
            Log.e(tag, "----RES------------------------------------------------");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(tag, "URL : " + clone.request().url());
            Log.e(tag, clone.protocol() + " " + clone.code());
            if (!TextUtils.isEmpty(clone.message())) Log.e(tag, "Message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.e(tag, "ContentType : " + mediaType.toString());
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
                            Log.e(tag, "content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
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
