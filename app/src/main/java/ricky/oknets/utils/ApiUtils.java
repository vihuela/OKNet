package ricky.oknets.utils;

import ricky.oknet.utils.ApiHelper;
import ricky.oknets.HttpApi;

/**
 * @author YaoWeihui on 2016/5/25.
 */
public enum ApiUtils {
    Instance;

    public HttpApi getApi() {
        return ApiHelper.get(HttpApi.class);
    }

}
