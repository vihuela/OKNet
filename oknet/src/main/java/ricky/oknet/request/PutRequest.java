package ricky.oknet.request;


import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import ricky.oknet.model.HttpHeaders;
import ricky.oknet.utils.HttpUtils;
import ricky.oknet.utils.OkLogger;

public class PutRequest extends BaseBodyRequest<PutRequest> {

    public PutRequest(String url) {
        super(url);
        method = "PUT";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        try {
            headers.put(HttpHeaders.HEAD_KEY_CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            OkLogger.e(e);
        }
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.put(requestBody).url(url).tag(tag).build();
    }
}