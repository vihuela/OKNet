package ricky.oknets;

import android.app.Application;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;

import java.util.logging.Level;

import ricky.oknet.OkGo;
import ricky.oknet.model.HttpHeaders;
import ricky.oknet.model.HttpParams;

/**
 * @author ricky.yao on 2016/5/10.
 */
public class OKApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //util
        Utils.init(this);
        ToastUtils.init(true);
        //net
        OkGo.init(this);


        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
//        params.put("commonParamsKey2", "这里支持中文参数");

        OkGo.getInstance()
                .baseUrl("http://server.jeasonlzy.com/OkHttpUtils/")
                .debug("OKNet", Level.INFO, true)                           //是否打开调试
                .addCommonHeaders(headers)                                  //设置全局公共头
                .addCommonParams(params);
    }
}
