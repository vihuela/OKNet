package ricky.oknets.utils;

import ricky.oknet.modeinterface.NetUtil;
import ricky.oknet.utils.ApiHelper;
import ricky.oknets.HttpApi;
import ricky.oknets.model.CommonRequest;

/**
 * @author YaoWeihui on 2016/5/25.
 */
public enum ApiUtils {
    Instance;

    public HttpApi getApi() {
        return ApiHelper.get(HttpApi.class/*, new NetUtil.ICustomerJsonBean<CommonRequest>() {
            @Override
            public CommonRequest onInterceptRequest(CommonRequest commonRequest) {
                commonRequest.token = "123456";
                CommonRequest.CheckSignBean checkSignBean = new CommonRequest.CheckSignBean();
                checkSignBean.appVersion = 123;
                checkSignBean.mac = "sdfskldfsld";
                commonRequest.checkSign = checkSignBean;
                return commonRequest;
            }
        }*/);
    }

}
