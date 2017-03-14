package ricky.oknet.request;


import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import ricky.oknet.model.HttpHeaders;
import ricky.oknet.utils.HttpUtils;
import ricky.oknet.utils.OkLogger;

public class OptionsRequest extends BaseBodyRequest<OptionsRequest> {

    public OptionsRequest(String url) {
        super(url);
        method = "OPTIONS";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        try {
            headers.put(HttpHeaders.HEAD_KEY_CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            OkLogger.e(e);
        }
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.method("OPTIONS", requestBody).url(url).tag(tag).build();
    }
}