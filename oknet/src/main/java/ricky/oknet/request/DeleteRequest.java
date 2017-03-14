package ricky.oknet.request;


import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import ricky.oknet.model.HttpHeaders;
import ricky.oknet.utils.HttpUtils;
import ricky.oknet.utils.OkLogger;

public class DeleteRequest extends BaseBodyRequest<DeleteRequest> {

    public DeleteRequest(String url) {
        super(url);
        method = "DELETE";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        try {
            headers.put(HttpHeaders.HEAD_KEY_CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            OkLogger.e(e);
        }
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.delete(requestBody).url(url).tag(tag).build();
    }
}