package ricky.oknets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Proxy;

import okhttp3.Request;
import okhttp3.Response;
import ricky.oknet.OkHttpUtils;
import ricky.oknet.cache.CacheManager;
import ricky.oknet.cache.CacheMode;
import ricky.oknet.modeinterface.NetRequest;
import ricky.oknet.modeinterface.NetUtil;
import ricky.oknet.utils.Cons;
import ricky.oknets.callback.DialogCallback;
import ricky.oknets.callback.JsonCallback;
import ricky.oknets.response.CityResponse;
import ricky.oknets.response.RequestInfo;
import ricky.oknets.utils.ApiUtils;

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


        /*normal();*/
        retrofit();

    }

    private void retrofit() {


        /*ApiUtils.Instance.getApi().cityList(5, "android").execute(new DialogCallback<CityResponse.DataBean>(this) {
            @Override
            public void onResponse(boolean isFromCache, CityResponse.DataBean dataBean, Request request, @Nullable Response response) {
                System.out.println();
            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {
                System.out.println();
            }
        });*/
        ApiUtils.Instance.getApi().method().execute(new JsonCallback<RequestInfo>() {
            @Override
            public void onResponse(boolean isFromCache, RequestInfo requestInfo, Request request, @Nullable Response response) {
                System.out.println();
            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {
                System.out.println();//可重写onError
            }
        });
    }

    private void normal() {
        url = "http://192.168.1.70/api/common/cityList?productLine=5&os=android";
        OkHttpUtils.get(url)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new DialogCallback<CityResponse.DataBean>(this) {
                    @Override
                    public void onSimpleError(Cons.Error error, String message) {
                        System.out.println();
                    }

                    @Override
                    public void onResponse(boolean isFromCache, CityResponse.DataBean dataBean, Request request, @Nullable Response response) {
                        System.out.println();
                    }
                });
    }
}
