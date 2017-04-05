package ricky.oknets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;

import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import ricky.oknet.OkGo;
import ricky.oknet.callback.FileCallback;
import ricky.oknet.convert.FileConvert;
import ricky.oknet.lifecycle.INetQueue;
import ricky.oknet.lifecycle.NetQueue;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.utils.Error;
import ricky.oknet.utils.OkLogger;
import ricky.oknets.callback.JsonCallback;
import ricky.oknets.callback.JsonConvert;
import ricky.oknets.callback.show.JsonCallback_Customer;
import ricky.oknets.callback.show.JsonCallback_Unknow;
import ricky.oknets.request.Request;
import ricky.oknets.utils.Api;

public class MainActivity extends AppCompatActivity {


    private INetQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mQueue = new NetQueue();
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
        }, mQueue);
    }

    public void postRx() {
        Api.getApi().post("param1").rx(new JsonConvert<Request.Res>() {
        }, new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        }, mQueue);
    }

    public void fileUpload() {
        File avatar = new File("/storage/emulated/0/DCIM/camera/IMG_20160823_112713.jpg");
        Api.getApi().fileUpload("ricky", avatar)
                .execute(new JsonCallback<Request.Res>() {

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
                }, mQueue);
    }

    public void fileUploadRx() {
        File avatar = new File("/storage/emulated/0/DCIM/camera/IMG_20160823_112713.jpg");
        Api.getApi().fileUpload("ricky", avatar)
                .rx(new JsonConvert<Request.Res>() {
                }, new JsonCallback<Request.Res>() {

                    @Override
                    public void success(Request.Res res, boolean fromCache) {
                        System.out.println();
                    }

                    @Override
                    public void error(Error error, String message) {
                        System.out.println();
                    }

                    //Rx下无进度显示
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
                        String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
                        String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);

                        System.out.println(downloadLength + "/" + totalLength);
                        System.out.println(netSpeed + "/S");
                        System.out.println((Math.round(progress * 10000) * 1.0f / 100) + "%");
                    }
                }, mQueue);
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
            public void onParsedError(Error error, String message) {
                super.onParsedError(error, message);
            }
        }, mQueue);

    }

    public void fileDownloadRx() {
        Api.getApi().fileDown("fucking_awesome").rx(new FileConvert(), new FileCallback("OkGo.apk") {
            @Override
            public void onBefore(BaseRequest request) {
                System.out.println("正在下载中");
            }

            @Override
            public void onSuccess(File file, Call call, Response response) {
                System.out.println("下载完成");
            }

            //Rx下无进度显示
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
            public void onParsedError(Error error, String message) {
                super.onParsedError(error, message);
            }
        }, mQueue);

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

        }, mQueue);
    }

    public void getRx() {
        Api.getApi().get("param1", 2).rx(new JsonConvert<Request.Res>() {
        }, new JsonCallback<Request.Res>() {
            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                super.error(error, message);
            }
        }, mQueue);
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
        }, mQueue);
    }

    public void jsonRx() {
        Api.getApi().json(new Request.Req()).rx(new JsonConvert<Request.Res>() {
        }, new JsonCallback<Request.Res>() {
            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                super.error(error, message);
            }
        }, mQueue);
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
        }, mQueue);
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
        }, mQueue);
    }

    public void head() {
        Api.getApi().head("param1").execute(new JsonCallback<String>() {

            @Override
            public void success(String res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        }, mQueue);
    }

    public void option() {
        Api.getApi().option("param1").execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        }, mQueue);
    }

    public void put() {
        Api.getApi().put("param1").execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        }, mQueue);
    }

    public void delete() {
        Api.getApi().delete("param1").execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }
        }, mQueue);
    }

    //没有对应的异常处理类
    private void error_unKnow() {
        Api.getApi().error_net("param1", 2).execute(new JsonCallback_Unknow<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                OkLogger.d("++++++++++++++++++++++" + "错误类型：" + error + " 文本:" + message);
            }

        }, mQueue);
    }

    //自定义异常
    private void error_customer() {

        Api.getApi().error_net("param1", 2).execute(new JsonCallback_Customer<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                OkLogger.d("++++++++++++++++++++++" + "错误类型：" + error + " 文本:" + message);
            }

        }, mQueue);

    }

    //程序内部异常，数据解析
    private void error_internal() {
        Api.getApi().error_internal("param1", 2).execute(new JsonCallback<Request.Res1>() {

            @Override
            public void success(Request.Res1 res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                OkLogger.d("++++++++++++++++++++++" + "错误类型：" + error + " 文本:" + message);
            }

        }, mQueue);
    }

    //服务端返回异常，404,500...
    private void error_server() {
        Api.getApi().error_server("param1", 2).execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                OkLogger.d("++++++++++++++++++++++" + "错误类型：" + error + " 文本:" + message);
            }

        }, mQueue);
    }

    //无网、超时、socket等
    private void error_net() {
        if (NetworkUtils.isConnected()) {
            ToastUtils.showShortToastSafe("请断开网络尝试");
            return;
        }
        Api.getApi().error_net("param1", 2).execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                OkLogger.d("++++++++++++++++++++++" + "错误类型：" + error + " 文本:" + message);
            }

        }, mQueue);
    }

    //api演示
    private void imageList() {
        Api.getApi().imageList(20, 0).execute(new JsonCallback<Request.Res2>() {

            @Override
            public void success(Request.Res2 res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
            }

        }, mQueue);
    }

    //api演示
    private void newsList1() {
        Api.getApi().getNewForLast().execute(new JsonCallback<Request.Res3>() {
            @Override
            public void success(Request.Res3 res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                super.error(error, message);
            }
        }, mQueue);
    }

    private void newsList2() {
        Api.getApi().getNewForDate("20170405").execute(new JsonCallback<Request.Res3>() {
            @Override
            public void success(Request.Res3 res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                super.error(error, message);
            }
        }, mQueue);
    }

    @OnClick({R.id.get, R.id.post, R.id.upFile, R.id.downFile, R.id.head, R.id.option, R.id.put, R.id.delete, R.id.json, R.id.string, R.id.getRx, R.id.postRx, R.id.upFileRx, R.id.downFileRx, R.id.jsonRx, R.id.clearCache, R.id.cancelRequest, R.id.error_net, R.id.error_server, R.id.error_internal, R.id.error_customer, R.id.error_unKnow, R.id.imageList, R.id.newsList1, R.id.newsList2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get:
                get();
                break;
            case R.id.post:
                post();
                break;
            case R.id.upFile:
                fileUpload();
                break;
            case R.id.downFile:
                fileDownload();
                break;
            case R.id.head:
                head();
                break;
            case R.id.option:
                option();
                break;
            case R.id.put:
                put();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.json:
                json();
                break;
            case R.id.string:
                string();
                break;
            case R.id.getRx:
                getRx();
                break;
            case R.id.postRx:
                postRx();
                break;
            case R.id.upFileRx:
                fileUploadRx();
                break;
            case R.id.downFileRx:
                fileDownloadRx();
                break;
            case R.id.jsonRx:
                jsonRx();
                break;
            case R.id.clearCache:
                OkGo.getInstance().clearCache();
                break;
            case R.id.cancelRequest:
                mQueue.cancel();
                break;
            case R.id.error_net:
                error_net();
                break;
            case R.id.error_server:
                error_server();
                break;
            case R.id.error_internal:
                error_internal();
                break;
            case R.id.error_customer:
                error_customer();
                break;
            case R.id.error_unKnow:
                error_unKnow();
                break;
            case R.id.imageList:
                imageList();
                break;
            case R.id.newsList1:
                newsList1();
                break;
            case R.id.newsList2:
                newsList2();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancel();
    }
}
