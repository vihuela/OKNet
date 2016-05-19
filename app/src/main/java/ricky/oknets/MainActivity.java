package ricky.oknets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Proxy;

import okhttp3.Request;
import okhttp3.Response;
import ricky.oknet.cache.CacheManager;
import ricky.oknet.callback.StringCallback;
import ricky.oknet.modeinterface.NetUtil;
import ricky.oknet.utils.Cons;

public class MainActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clear(View v) {
        CacheManager.INSTANCE.clear();
    }

    public void exec(View view) {
        url = "http://192.168.1.70/api/common/cityList?productLine=5&os=android";

//        OkHttpUtils.get(url)
//                .cacheMode(CacheMode.DEFAULT)
//
//                .execute(new DialogCallback<CityResponse.DataBean>(this) {
//                    @Override
//                    public void onSimpleError(Cons.Error error, String message) {
//                        System.out.println();
//                    }
//
//                    @Override
//                    public void onResponse(boolean isFromCache, CityResponse.DataBean dataBean, Request request, @Nullable Response response) {
//                        System.out.println();
//                    }
//                });
        NetUtil netUtil = new NetUtil();
        HttpApi apiIntance = (HttpApi) Proxy.newProxyInstance(
                HttpApi.class.getClassLoader(),
                new Class[]{HttpApi.class},
                netUtil);
        apiIntance.test(5,"android").beginRequest(new StringCallback() {
            @Override
            public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {

            }
        });
//        OkHttpUtils.get()
//                .execute(new FileCallback() {
//                    @Override
//                    public void onResponse(boolean isFromCache, File file, Request request, @Nullable Response response) {
//
//                    }
//
//                    @Override
//                    public void onSimpleError(Cons.Error error, String message) {
//
//                    }
//                });
    }
}
