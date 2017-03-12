package ricky.oknets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;
import ricky.oknet.OkGo;
import ricky.oknet.cache.CacheManager;
import ricky.oknet.callback.FileCallback;
import ricky.oknet.lifecycle.INetViewLifecycle;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.retrofit.Net;
import ricky.oknet.utils.Error;
import ricky.oknets.callback.JsonCallback;
import ricky.oknets.request.Request;
import ricky.oknets.utils.Api;

public class MainActivity extends AppCompatActivity implements INetViewLifecycle {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clear(View v) {
        CacheManager.INSTANCE.clear();
    }

    public void exec(View view) {
        fileUpload();
    }


    public void post() {
        Api.getApi().post("param1").execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        });
    }

    public void fileUpload() {
        File avatar = new File("/storage/emulated/0/DCIM/camera/IMG_20160823_112713.jpg");
        final Net<Request.Res> net = Api.getApi().fileUpload("ricky", avatar, avatar);
        net.execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
                String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
                String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);

                System.out.println(downloadLength + "/" + totalLength);
                System.out.println(netSpeed + "/S");
                System.out.println((Math.round(progress * 10000) * 1.0f / 100) + "%");
            }
        });
        OkGo.getInstance().getDelivery().postDelayed(new Runnable() {
            @Override
            public void run() {
                net.cancel();
            }
        }, 3000);
    }

    public void fileDownload() {
        Api.getApi().fileDown("fucking_awesome").execute(new FileCallback("OkGo.apk") {
            @Override
            public void onBefore(BaseRequest request) {
                System.out.println("正在下载中");
            }

            @Override
            public void onSuccess(File file, Call call, Response response) {
                System.out.println("下载完成");
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
                String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
                String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);

                System.out.println(downloadLength + "/" + totalLength);
                System.out.println(netSpeed + "/S");
                System.out.println((Math.round(progress * 10000) * 1.0f / 100) + "%");
            }

            @Override
            public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(call, response, e);
                System.out.println("下载出错");
            }
        });

    }

    public void get() {
        Api.getApi().get("param1", 2).execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }

        });
    }

    public void string() {
        Api.getApi().string("abcdefg").execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        });
    }

    public void json() {
        Api.getApi().json(new Request.Req()).execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        });
    }

    public void cache() {
        Api.getApi().cache().execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
