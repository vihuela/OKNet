package ricky.oknets;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import okhttp3.Request;
import okhttp3.Response;
import ricky.oknet.OkHttpUtils;
import ricky.oknet.cache.CacheManager;
import ricky.oknet.cache.CacheMode;
import ricky.oknet.callback.FileCallback;
import ricky.oknet.lifecycle.NetLifecycleMgr;
import ricky.oknet.lifecycle.OKNetBehavior;
import ricky.oknet.lifecycle.INetViewLifecycle;
import ricky.oknet.modeinterface.NetRequest;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.utils.Cons;
import ricky.oknets.callback.DialogCallback;
import ricky.oknets.callback.JsonCallback;
import ricky.oknets.model.RequestBean;
import ricky.oknets.response.CityResponse;
import ricky.oknets.response.CommonBen;
import ricky.oknets.response.RequestInfo;
import ricky.oknets.utils.ApiUtils;

public class MainActivity extends AppCompatActivity implements INetViewLifecycle{

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


//        normal();

//        retrofit();

        //downTest

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        } else {
//            downTest();
//        }

        //uploadTest
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            uploadTest();
        }*/
        //uploadJsonTest
        uploadJsonTest();
    }

    private void uploadJsonTest() {
        RequestBean requestBean = new RequestBean();
        NetRequest<CommonBen> commonBenNetRequest = ApiUtils.Instance.getApi().postJson(requestBean);
        commonBenNetRequest.bind(this).execute(new JsonCallback<CommonBen>() {
            @Override
            public void onResponse(boolean isFromCache, CommonBen commonBen, Request request, @Nullable Response response) {
                System.out.println();
            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {
                System.out.println();
            }
        });

    }

    private void uploadTest() {
        File f = new File("/storage/emulated/0/WorldGo/microMsg/image/microMsg.1462347415447.jpg");
        ApiUtils.Instance.getApi().upload("param1", f).execute(new JsonCallback<RequestInfo>() {
            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onResponse(boolean isFromCache, RequestInfo requestInfo, Request request, @Nullable Response response) {
                System.out.println();
            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {
                System.out.println();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case 1:
                    downTest();
                    break;
                case 2:
                    uploadTest();
                    break;
            }
        }
    }

    private void downTest() {

        ApiUtils.Instance.getApi().downFile().execute(new FileCallback("test.apk") {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
            }

            @Override
            public void onResponse(boolean isFromCache, File file, Request request, @Nullable Response response) {
                System.out.println();
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {
                System.out.println();
            }
        });
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //cancel request
        NetLifecycleMgr.Instance.onNetBehavior(this,OKNetBehavior.DESTROY);
    }
}
