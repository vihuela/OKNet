package ricky.oknets.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.view.Window;


import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;
import ricky.oknet.request.BaseRequest;

/**
 * 对于网络请求是否需要弹出进度对话框
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {

    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    public DialogCallback(Activity activity, Type type) {
        super(type);
        initDialog(activity);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(boolean isFromCache, @Nullable T t, Call call, @Nullable Response response, @Nullable Exception e) {
        super.onAfter(isFromCache, t, call, response, e);
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
