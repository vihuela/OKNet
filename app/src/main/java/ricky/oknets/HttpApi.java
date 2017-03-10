package ricky.oknets;

import java.io.File;

import ricky.oknet.cache.CacheMode;
import ricky.oknet.retrofit.NetRequest;
import ricky.oknet.retrofit.anno.CACHE;
import ricky.oknet.retrofit.anno.GET;
import ricky.oknet.retrofit.anno.JSON;
import ricky.oknet.retrofit.anno.POST;
import ricky.oknet.retrofit.anno.Param;
import ricky.oknets.model.RequestBean;
import ricky.oknets.response.CityResponse;
import ricky.oknets.response.CommonBen;
import ricky.oknets.response.RequestInfo;

public interface HttpApi {

    @GET("http://192.168.1.70/api/common/cityList")
//?productLine=5&os=android
    NetRequest<CityResponse.DataBean> cityList(@Param("productLine") int productLine, @Param("os") String os);

    @CACHE(CacheMode.FIRST_CACHE_THEN_REQUEST)
    @GET("method")
    NetRequest<RequestInfo> method();

    @GET("download")
    NetRequest<File> downFile();

    @POST("upload")
    NetRequest<RequestInfo> upload(@Param("param1") String param1, @Param("file") File file);

    @JSON("http://120.24.233.226/ztx/v100/guide/getGuideCommments")
    NetRequest<CommonBen> postJson(RequestBean requestBean);


}
