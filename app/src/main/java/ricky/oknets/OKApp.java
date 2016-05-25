package ricky.oknets;

import android.app.Application;

import ricky.oknet.OkHttpUtils;
import ricky.oknet.cookie.store.MemoryCookieStore;
import ricky.oknet.cookie.store.PersistentCookieStore;
import ricky.oknet.model.HttpParams;

/**
 * @author YaoWeihui on 2016/5/10.
 */
public class OKApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpUtils.init(this);

       /* HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");*/
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        params.put("commonParamsKey2", "这里支持中文参数");

        OkHttpUtils.getInstance()//
                .baseUrl("http://server.jeasonlzy.com/OkHttpUtils/")
                .debug(true, true, "OKNet")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)            //全局的写入超时时间
                /*.addCommonHeaders(headers)   */                                      //设置全局公共头
//                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                   //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonParams(params);
    }
}
