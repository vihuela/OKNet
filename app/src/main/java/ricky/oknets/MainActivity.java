package ricky.oknets;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import ricky.oknet.OkHttpUtils;
import ricky.oknet.cache.CacheManager;
import ricky.oknet.cache.CacheMode;
import ricky.oknet.utils.Cons;
import ricky.oknets.callback.DialogCallback;
import ricky.oknets.response.CityResponse;

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
