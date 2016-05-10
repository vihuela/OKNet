package ricky.oknet.request;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;

public class OptionsRequest extends BaseRequest<OptionsRequest> {

    private RequestBody requestBody;

    public OptionsRequest(String url) {
        super(url);
    }

    public OptionsRequest requestBody(@NonNull RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    @Override
    protected RequestBody generateRequestBody() {
        if (requestBody != null) return requestBody;
        return generateMultipartRequestBody();
    }

    @Override
    protected Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = new Request.Builder();
        try {
            headers.put("Content-Length", String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        appendHeaders(requestBuilder);
        return requestBuilder.method("OPTIONS", requestBody).url(url).tag(tag).build();
    }
}
