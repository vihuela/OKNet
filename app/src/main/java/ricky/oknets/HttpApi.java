package ricky.oknets;

import java.io.File;

import ricky.oknet.cache.CacheMode;
import ricky.oknet.retrofit.Net;
import ricky.oknet.retrofit.anno.CACHE;
import ricky.oknet.retrofit.anno.CacheTime;
import ricky.oknet.retrofit.anno.DELETE;
import ricky.oknet.retrofit.anno.GET;
import ricky.oknet.retrofit.anno.HEAD;
import ricky.oknet.retrofit.anno.HEADER;
import ricky.oknet.retrofit.anno.JSON;
import ricky.oknet.retrofit.anno.JsonParam;
import ricky.oknet.retrofit.anno.OPTIONS;
import ricky.oknet.retrofit.anno.POST;
import ricky.oknet.retrofit.anno.PUT;
import ricky.oknet.retrofit.anno.Param;
import ricky.oknet.retrofit.anno.STRING;
import ricky.oknet.retrofit.anno.StringParam;
import ricky.oknets.request.Request;

public interface HttpApi {

    @GET("method")
    @HEADER(key = "header_key", value = "header_val")
    @CACHE(CacheMode.FIRST_CACHE_THEN_REQUEST)
    Net<Request.Res> get(@Param("param1") String param1, @Param("param2") int param2);

    @POST("method")
    Net<Request.Res> post(@Param("param1") String param1);

    @HEAD("method")
    Net<String> head(@Param("param1") String param1);

    @OPTIONS("method")
    Net<Request.Res> option(@Param("param1") String param1);

    @PUT("method")
    Net<Request.Res> put(@Param("param1") String param1);

    @DELETE("method")
    Net<Request.Res> delete(@Param("param1") String param1);

    @GET("cache")
    @CACHE(CacheMode.REQUEST_FAILED_READ_CACHE)
    @CacheTime(10 * 1000)
    Net<Request.Res> cache();

    @JSON("uploadString")
    Net<Request.Res> json(@JsonParam Request.Req req);

    @STRING("uploadString")
    Net<Request.Res> string(@StringParam String req);

    @GET("download")
    @HEADER(key = "header_key", value = "header_val")
    Net fileDown(@Param("param") String param1);

    @POST("upload")
    @HEADER(key = "header_key", value = "header_val")
    Net<Request.Res> fileUpload(@Param("nick") String nick, @Param("avatar1") File avatar1,@Param("avatar2") File avatar2);


}
