package ricky.oknet.request;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Get请求的实现类，注意需要传入本类的泛型
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    protected Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = new Request.Builder();
        appendHeaders(requestBuilder);
        url = createUrlFromParams(url, params.urlParamsMap);
        return requestBuilder.get().url(url).tag(tag).build();
    }
}