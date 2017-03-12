package ricky.oknets.utils;

import ricky.oknet.retrofit.ApiProxyHandler;
import ricky.oknet.utils.ApiHelper;
import ricky.oknets.HttpApi;
import ricky.oknets.common.CommonRequest;

/**
 * @author ricky.yao on 2016/5/25.
 */
public class Api {

    public static HttpApi getApi() {
        return ApiHelper.get(HttpApi.class, new ApiProxyHandler.ICustomerJsonBean<CommonRequest>() {
            @Override
            public CommonRequest onInterceptRequest(CommonRequest commonRequest) {
                commonRequest.token = "1234567";
                return commonRequest;
            }
        });
    }

}
